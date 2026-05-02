package com.internship.tool.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final int MAX_REQUESTS_PER_MINUTE = 30;
    
    // Simplistic in-memory map for rate limiting by IP
    private Map<String, RequestData> clientRequests = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();
        
        clientRequests.putIfAbsent(clientIp, new RequestData(currentTime, new AtomicInteger(0)));
        RequestData requestData = clientRequests.get(clientIp);

        if (currentTime - requestData.windowStartTime > 60000) {
            requestData.windowStartTime = currentTime;
            requestData.count.set(0);
        }

        if (requestData.count.incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Maximum 30 requests per minute.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private static class RequestData {
        long windowStartTime;
        AtomicInteger count;

        RequestData(long windowStartTime, AtomicInteger count) {
            this.windowStartTime = windowStartTime;
            this.count = count;
        }
    }
}
