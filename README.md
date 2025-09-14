# Employee_Management_System

## How to Run

1. **Clone the repository**  
   ```
   git clone https://github.com/vismayavijayakumaran/Employee_Management_System.git
   cd Employee_Management_System
   git checkout develop 

  
   ```

2. **Configure the database**  
   - Update your `application.properties` or `application.yml` with your database connection details.

3. **Build and run the application**  
   ```
   ./mvnw spring-boot:run
   ```
   or
   ```
   mvn spring-boot:run
   ```

## API Endpoints

### Employees

- **Create Employee:**  
  `POST /api/employees`  
  Request body: Employee JSON

- **Get All Employees (paginated):**  
  `GET /api/employees?page=0&size=20`

- **Get Employee Lookup (ID & Name only, paginated):**  
  `GET /api/employees?lookup=true&page=0&size=20`

- **Update Employee:**  
  `PUT /api/employees/{id}`  
  Request body: EmployeeRequest JSON

- **Move Employee to Another Department:**  
  `PATCH /api/employees/{id}/department`  
  Request body:  
  ```json
  {
    "newDepartmentId": "department-uuid"
  }
  ```

### Departments

- **Create Department:**  
  `POST /api/departments`  
  Request body: Department JSON

- **Get All Departments (paginated):**  
  `GET /api/departments?page=0&size=20`

- **Get Department by ID:**  
  `GET /api/departments/{id}`

- **Get Department with Employees:**  
  `GET /api/departments/{id}?expand=employee`

- **Update Department:**  
  `PUT /api/departments/{id}`  
  Request body: DepartmentRequest JSON

- **Delete Department:**  
  `DELETE /api/departments/{id}`

## Database Script

```sql
CREATE TABLE public.departments (
    id uuid NOT NULL,
    created_at timestamp DEFAULT now() NOT NULL,
    "name" varchar(255) NULL,
    head_id uuid NULL,
    CONSTRAINT departments_pkey PRIMARY KEY (id),
    CONSTRAINT ukrpbl99tstvgrcsdpfsx34iheb UNIQUE (head_id)
);

CREATE TABLE public.employees (
    id uuid NOT NULL,
    address varchar(255) NULL,
    date_of_birth date NULL,
    joining_date date NULL,
    "name" varchar(255) NULL,
    "role" varchar(255) NULL,
    salary float8 NULL,
    yearly_bonus_percentage float8 NULL,
    department_id uuid NULL,
    manager_id uuid NULL,
    created_at timestamp DEFAULT now() NOT NULL,
    email varchar(255) NULL,
    CONSTRAINT employees_pkey PRIMARY KEY (id)
);

-- public.employees foreign keys

ALTER TABLE public.employees ADD CONSTRAINT fkgy4qe3dnqrm3ktd76sxp7n4c2 FOREIGN KEY (department_id) REFERENCES public.departments(id);
ALTER TABLE public.employees ADD CONSTRAINT fki4365uo9af35g7jtbc2rteukt FOREIGN KEY (manager_id) REFERENCES public.employees(id);
```

## Notes

- All endpoints return JSON.
- Use tools like Postman to test the APIs.
- Pagination parameters (`page`, `size`) are optional.
- For lookup endpoints, only employee ID and name
