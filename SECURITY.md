# SECURITY REPORT

## Threats Identified
- SQL Injection
- Prompt Injection
- Unauthorized Access
- Rate Limit Abuse

## Tests Performed
- API testing using Postman: Verified that unprotected endpoints allowed unauthorized access.
- Injection testing: Sent malicious SQL queries (' OR '1'='1) to login fields and tested AI describe routes with jailbreak prompts.
- Input validation checks: Tested boundaries and invalid data types on the policy creation API.
- Rate Limit testing: Ran automated python scripts to flood the API and monitor response codes.

## Fixes Verified
- Input sanitization applied: Checked JPA parameterization and verified AI prompt sanitization logic strips HTML and blocks jailbreak keywords.
- JWT authentication working: Successfully configured the SecurityConfig to enforce the JwtAuthFilter. Endpoints now correctly return 401 Unauthorized for missing tokens.
- Rate limiting implemented: Added a RateLimitingFilter to restrict users to 30 req/min to prevent abuse.

## Result
All major vulnerabilities handled.

## Residual Risks
- AI output unpredictability
