# list-all-employees

## Architecture & Components
- `EmployeeDto` (record): empId, empName, empSalary, empDepartment
- `EmployeeService` / `EmployeeServiceImpl`: reads `employee.csv` from classpath (src/main/resources), skips header
- `EmployeeFileNotFoundException`: thrown when file is missing
- `GlobalExceptionHandler` (`@RestControllerAdvice`): maps it to HTTP 401
- `EmployeeController` (`@RestController`)

## API Contract
GET /api/v1/empmanager/list-all-employees
- 200: `[{"empId":101,"empName":"John Doe","empSalary":75000,"empDepartment":"Engineering"}]`
- 401: `{"error":"employee.csv file not found"}`

## Data Layer
CSV columns: emp_id,emp_name,emp_salary,emp_department. No DB.

## Verification Plan
- Service unit tests: parsing, missing file -> exception
- MockMvc tests: 200 with list, 401 when service throws
