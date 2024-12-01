# TestMsigNando

# README

## Steps to Run the Project

1. **Start Docker Container**:
   - Navigate to the directory containing the `docker-compose.yml` file.
   - Run the following command to start the Docker containers:
     ```bash
     docker-compose up
     ```

2. **Connect to PostgreSQL using DBeaver**:
   - Open **DBeaver** and create a new connection for PostgreSQL.
   - Use the database settings provided in the `docker-compose.yml` file to establish the connection.

3. **Create Tables in PostgreSQL**:
   - Copy the SQL script from the file named `Script Table in Postgre`.
   - Execute the script in the PostgreSQL database to create the `teacher` and `student` tables.

4. **Run the Spring Boot Projects**:
   - First, run the **Teacher Service/Project**.
   - After the teacher service is up, run the **Student Service/Project**.

5. **Test APIs Using Postman**:
   - Import the provided **Postman collection file** into the Postman tool.
   - Use the imported collection to test the APIs for both teacher and student services.

6. **Test the UI**:
   - Open the folder `web-python` in your preferred IDE.

7. **Set Up Python Environment**:
   - Ensure the latest version of Python is installed on your machine.
   - Ensure `pip` (Python package manager) is installed.

8. **Install Dependencies**:
   - Open a terminal and navigate to the `web-python` folder:
     ```bash
     cd web-python
     ```
   - Install Flask and Requests using pip:
     ```bash
     pip install flask requests
     ```

9. **Run the Flask Application**:
   - Start the Flask application by running:
     ```bash
     python app.py
     ```

10. **Access the Web Application**:
    - Open your browser and navigate to:
      ```
      http://127.0.0.1:5000
      ```

## Notes
- Ensure the Docker containers are running for PostgreSQL before starting the Spring Boot services.
- Verify that the database and table creation steps have been completed before running the Spring Boot projects.
- Use the provided Postman collection to validate the API functionality.
- The UI depends on the Python Flask backend, so ensure Flask is properly installed and running.

If you encounter any issues, please refer to the error logs or ensure all dependencies are correctly set up. Happy coding! 😊
