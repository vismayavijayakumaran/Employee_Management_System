# Employee Management System

This project is an Employee Management System built using Spring Boot. It provides a RESTful API for managing employees and departments, allowing for the creation, updating, deletion, and retrieval of records.

## Features

- Manage Employees
  - Create, update, delete, and fetch employee records.
  - Pagination support for listing employees.

- Manage Departments
  - Add, update, delete, and fetch department records.
  - Pagination support for listing departments.

## Technologies Used

- Spring Boot
- Spring Data JPA
- H2 Database (or any other database of your choice)
- Maven

## Setup Instructions

1. Clone the repository:
   ```
   git clone <repository-url>
   ```

2. Navigate to the project directory:
   ```
   cd employee-management-system
   ```

3. Build the project using Maven:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

5. Access the API documentation (if using Swagger or similar) or test the endpoints using Postman or any other API client.

## API Endpoints

### Employee Endpoints

- `POST /employees` - Create a new employee
- `GET /employees` - Fetch all employees with pagination
- `GET /employees/{id}` - Fetch an employee by ID
- `PUT /employees/{id}` - Update an existing employee
- `DELETE /employees/{id}` - Delete an employee

### Department Endpoints

- `POST /departments` - Create a new department
- `GET /departments` - Fetch all departments with pagination
- `GET /departments/{id}` - Fetch a department by ID
- `PUT /departments/{id}` - Update an existing department
- `DELETE /departments/{id}` - Delete a department

## License

This project is licensed under the MIT License.