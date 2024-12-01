from flask import Flask, render_template, jsonify
import requests

app = Flask(__name__)

# URL API untuk Teachers dan Students
TEACHER_API_URL = "http://localhost:8081/api/teachers"
STUDENT_API_URL = "http://localhost:8082/api/students"

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/get-teachers', methods=['GET'])
def get_teachers():
    try:
        response = requests.get(TEACHER_API_URL, headers={"Accept": "application/json"})
        if response.status_code == 200:
            return jsonify(response.json())
        else:
            return jsonify({"error": f"Failed to fetch teachers. Status: {response.status_code}"}), 500
    except requests.exceptions.RequestException as e:
        return jsonify({"error": str(e)}), 500

@app.route('/get-students', methods=['GET'])
def get_students():
    try:
        response = requests.get(STUDENT_API_URL, headers={"Accept": "application/json"})
        if response.status_code == 200:
            return jsonify(response.json())
        else:
            return jsonify({"error": f"Failed to fetch students. Status: {response.status_code}"}), 500
    except requests.exceptions.RequestException as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
