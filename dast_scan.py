import requests
import json
import time

TARGET_URL = "http://localhost:8080"
API_URL = f"{TARGET_URL}/api"

print("==================================================")
print("  OWASP-Style Dynamic Application Security Test   ")
print("  Target: http://localhost:8080                   ")
print("==================================================\n")

vulnerabilities = []

# 1. Check Security Headers
print("[*] Scanning Security Headers...")
try:
    res = requests.get(TARGET_URL)
    headers = res.headers
    
    expected_headers = [
        "X-Content-Type-Options",
        "X-Frame-Options",
        "X-XSS-Protection",
        "Strict-Transport-Security",
        "Content-Security-Policy"
    ]
    
    for header in expected_headers:
        if header not in headers:
            vulnerabilities.append({
                "type": "Missing Security Header",
                "risk": "Low/Medium",
                "description": f"The response does not include the {header} header."
            })
    print("  [+] Header scan complete.")
except Exception as e:
    print(f"  [-] Failed to reach target: {e}")

# 2. Check Information Leakage (Server Header)
print("[*] Checking for Information Leakage...")
if 'Server' in headers:
    vulnerabilities.append({
        "type": "Information Disclosure",
        "risk": "Low",
        "description": f"Server header reveals backend technology: {headers['Server']}"
    })
print("  [+] Info leakage scan complete.")

# 3. Test Authentication Bypass (Broken Access Control)
print("[*] Testing Broken Access Control...")
res = requests.get(f"{API_URL}/policy-records")
if res.status_code == 200:
    vulnerabilities.append({
        "type": "Broken Access Control",
        "risk": "High",
        "description": "Unauthenticated user could access /api/policy-records"
    })
else:
    print(f"  [+] /api/policy-records correctly rejected unauthorized access (Status {res.status_code}).")

# 4. Test SQL Injection on Login
print("[*] Testing SQL Injection (Authentication Bypass)...")
payloads = ["admin' OR '1'='1", "admin\" OR 1=1--"]
for payload in payloads:
    data = {"username": payload, "password": "password"}
    res = requests.post(f"{API_URL}/auth/login", json=data)
    if res.status_code == 200:
        vulnerabilities.append({
            "type": "SQL Injection",
            "risk": "Critical",
            "description": f"Login bypassed using SQLi payload: {payload}"
        })
print("  [+] SQLi scan complete.")

# 5. Test Rate Limiting
print("[*] Testing Rate Limiting / Denial of Service...")
hit_rate_limit = False
for i in range(35):
    res = requests.post(f"{API_URL}/auth/login", json={"username":"test", "password":"password"})
    if res.status_code == 429:
        hit_rate_limit = True
        break

if hit_rate_limit:
    print("  [+] Rate limit (429) successfully triggered, preventing brute-force.")
else:
    vulnerabilities.append({
        "type": "Missing Rate Limiting",
        "risk": "Medium",
        "description": "Application did not throttle requests after 35 rapid login attempts."
    })

print("\n==================================================")
print("                DAST SCAN REPORT                  ")
print("==================================================")

if not vulnerabilities:
    print("No critical vulnerabilities found! The application is secure.")
else:
    print(f"Found {len(vulnerabilities)} potential issue(s):\n")
    for idx, vuln in enumerate(vulnerabilities):
        print(f"[{idx+1}] {vuln['type']} (Risk: {vuln['risk']})")
        print(f"    - {vuln['description']}")
print("==================================================\n")
