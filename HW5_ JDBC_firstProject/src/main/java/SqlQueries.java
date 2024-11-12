class SqlQueries {
    public static final String INSERT_EMPLOYEE = "INSERT INTO Employees (first_name, last_name, email, department, salary, hire_date) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SELECT_EMAIL_COUNT = "SELECT COUNT(*) FROM Employees WHERE email = ?";
    public static final String UPDATE_EMPLOYEE = "UPDATE Employees SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ?, hire_date = ? WHERE id = ?";
    public static final String DELETE_EMPLOYEE = "DELETE FROM Employees WHERE id = ?";
    public static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM Employees";
    public static final String SELECT_EMPLOYEES_BY_DEPARTMENT = "SELECT * FROM Employees WHERE department = ?";
    public static final String SELECT_EMPLOYEES_WITH_SALARY_ABOVE = "SELECT * FROM Employees WHERE salary > ?";
}
