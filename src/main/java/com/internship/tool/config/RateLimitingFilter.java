package com.internship.tool.config;

import com.internship.tool.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final int MAX_REQUESTS_PER_MINUTE = 30;
    private final java.util.Map<String, Integer> fallbackCache = new java.util.concurrent.ConcurrentHashMap<>();
    private final java.util.Map<String, Long> fallbackTimestamps = new java.util.concurrent.ConcurrentHashMap<>();

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String identifier = request.getRemoteAddr();

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String username = jwtUtil.extractUsername(token);
                if (username != null) {
                    identifier = username;
                }
            } catch (Exception e) {
                // Ignore token parsing errors
            }
        }

        String key = "rate_limit:" + identifier;
        long count = 0;

        try {
            Long redisCount = redisTemplate.opsForValue().increment(key);
            if (redisCount != null && redisCount == 1) {
                redisTemplate.expire(key, Duration.ofMinutes(1));
            }
            count = redisCount != null ? redisCount : 0;
        } catch (Exception e) {
            // Redis is down, use fallback in-memory cache
            long now = System.currentTimeMillis();
            fallbackTimestamps.putIfAbsent(key, now);
            if (now - fallbackTimestamps.get(key) > 60000) {
                fallbackCache.put(key, 0);
                fallbackTimestamps.put(key, now);
            }
            count = fallbackCache.getOrDefault(key, 0) + 1;
            fallbackCache.put(key, (int) count);
        }

        if (count > MAX_REQUESTS_PER_MINUTE) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Maximum 30 requests per minute.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
