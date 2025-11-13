import requests
import json

url = "http://localhost:5000"
payload = {
    "jsonrpc": "2.0",
    "method": "add",
    "params": [5, 3],
    "id": 1
}

try:
    response = requests.post(url, json=payload, headers={"Content-Type": "application/json"})
    print(f"Status Code: {response.status_code}")
    print(f"Response Text: {response.text}")
    print(f"Response JSON: {response.json()}")
except Exception as e:
    print(f"Error: {e}")

