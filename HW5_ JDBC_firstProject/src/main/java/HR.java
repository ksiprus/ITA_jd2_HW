import java.sql.*;
import java.sql.Date;

public class HR {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/by_ksiprus"; // Replace with your database URL
        String user = "root"; // Replace with your username
        String password = "12341234"; // Replace with your password
        return DriverManager.getConnection(url, user, password);
    }

    public void addEmployee(String firstName, String lastName, String email, String department, double salary, Date hireDate) {
        if (isEmailExists(email)) {
            System.out.println("Сотрудник с таким email уже существует: " + email);
            return;
        }
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlQueries.INSERT_EMPLOYEE)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, department);
            pstmt.setDouble(5, salary);
            pstmt.setDate(6, hireDate);
            pstmt.executeUpdate();
            System.out.println("Сотрудник добавлен: " + firstName + " " + lastName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isEmailExists(String email) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlQueries.SELECT_EMAIL_COUNT)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateEmployee(int id, String firstName, String lastName, String email, String department, double salary, Date hireDate) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlQueries.UPDATE_EMPLOYEE)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, department);
            pstmt.setDouble(5, salary);
            pstmt.setDate(6, hireDate);
            pstmt.setInt(7, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Данные сотрудника обновлены: " + firstName + " " + lastName);
            } else {
                System.out.println("Сотрудник с ID " + id + " не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlQueries.DELETE_EMPLOYEE)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Сотрудник с ID " + id + " был успешно удалён.");
            } else {
                System.out.println("Сотрудник с ID " + id + " не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllEmployees() {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlQueries.SELECT_ALL_EMPLOYEES);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("Все сотрудники:");
            displayResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findEmployeesByDepartment(String department) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlQueries.SELECT_EMPLOYEES_BY_DEPARTMENT)) {
            pstmt.setString(1, department);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Сотрудники в отделе " + department + ":");
                displayResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findEmployeesWithSalaryAbove(double salaryThreshold) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlQueries.SELECT_EMPLOYEES_WITH_SALARY_ABOVE)) {
            pstmt.setDouble(1, salaryThreshold);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Сотрудники с зарплатой выше " + salaryThreshold + ":");
                displayResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayResultSet(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") +
                    ", Name: " + rs.getString("first_name") + " " + rs.getString("last_name") +
                    ", Email: " + rs.getString("email") +
                    ", Department: " + rs.getString("department") +
                    ", Salary: " + String.format("%.2f", rs.getDouble("salary")) +
                    ", Hire Date: " + rs.getDate("hire_date"));
        }
    }
}
