import os
import json
import requests
import pandas as pd
from datetime import datetime

# ---------------------------------------------------
# Load test cases
# ---------------------------------------------------

base_dir = os.path.dirname(os.path.abspath(__file__))
json_path = os.path.join(base_dir, "test_cases.json")

with open(json_path) as f:
    test_cases = json.load(f)

# ---------------------------------------------------
# Variables to store TC1 response values
# ---------------------------------------------------

assessment_id = None
completed_at = None

results = []

print("\nStarting API tests...\n")

# ---------------------------------------------------
# Execute test cases
# ---------------------------------------------------

for test in test_cases:

    test_id = test.get("test_id")
    description = test.get("description")
    endpoint = test.get("endpoint")
    method = test.get("method")
    expected_status = test.get("expected_status")

    # Deep copy request body
    body = json.loads(json.dumps(test.get("body", {})))

    # ---------------------------------------------------
    # Inject TC1 values into TC15
    # ---------------------------------------------------

    if test_id == "TC15":

        print("\nInjecting TC1 values into TC15")

        body["assessmentId"] = assessment_id
        body["completedAt"] = completed_at
        body["assessmentStatus"] = "Completed"

    try:

        print(f"\nRunning {test_id}")

        if body:
            print("Request Body:", body)

        start_time = datetime.now()

        if method == "POST":
            response = requests.post(
                endpoint,
                json=body,
                headers={"Content-Type": "application/json"}
            )

        elif method == "GET":
            response = requests.get(endpoint)

        else:
            raise Exception("Unsupported HTTP method")

        end_time = datetime.now()

        response_time = (end_time - start_time).total_seconds() * 1000
        actual_status = response.status_code
        response_body = response.text

        result = "PASS" if actual_status == expected_status else "FAIL"

        # ---------------------------------------------------
        # Capture TC1 response values
        # ---------------------------------------------------

        if test_id == "TC1" and actual_status == 200:

            try:

                data = json.loads(response.text)

                assessment_id = data["assessmentId"]
                completed_at = data["completedAt"]

                print("\nCaptured from TC1:")
                print("assessmentId =", assessment_id)
                print("completedAt =", completed_at)

            except Exception as e:

                print("Error extracting TC1 values:", e)

        results.append({
            "Test ID": test_id,
            "Description": description,
            "Endpoint": endpoint,
            "Method": method,
            "Expected Status": expected_status,
            "Actual Status": actual_status,
            "Result": result,
            "Response Time (ms)": round(response_time, 2),
            "Response Body": response_body[:500]
        })

        print(f"{test_id} → {result}")

    except Exception as e:

        results.append({
            "Test ID": test_id,
            "Description": description,
            "Endpoint": endpoint,
            "Method": method,
            "Expected Status": expected_status,
            "Actual Status": "ERROR",
            "Result": "FAIL",
            "Response Time (ms)": 0,
            "Response Body": str(e)
        })

        print(f"{test_id} → ERROR")

# ---------------------------------------------------
# Generate Excel report
# ---------------------------------------------------

print("\nAll tests executed")

df = pd.DataFrame(results)

timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
report_name = f"api_test_report_{timestamp}.xlsx"
report_path = os.path.join(base_dir, report_name)

df.to_excel(report_path, index=False)

print("\nExcel report generated:")
print(report_path)