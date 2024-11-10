import java.sql.*;

public class HR {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/OnlyMyShems"; // Замените на свой URL к базе данных
        String user = "root"; // Замените на свое имя пользователя
        String password = "12345678"; // Замените на свой пароль
        return DriverManager.getConnection(url, user, password);
    }

    public void addEmployee(String firstName, String lastName, String email, String department, double salary, Date hireDate) {
        // Проверка на существование
        if (isEmailExists(email)) {
            System.out.println("Сотрудник с таким email уже существует: " + email);
            return; // Прерывание метода
        }

        String sql = "INSERT INTO employees (first_name, last_name, email, department, salary, hire_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        String sql = "SELECT COUNT(*) FROM employees WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Если найден хотя бы один, то email существует
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Email не найден
    }

    public void updateEmployee(int id, String firstName, String lastName, String email, String department, double salary, Date hireDate) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ?, hire_date = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM employees";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("Все сотрудники:");
            displayResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findEmployeesByDepartment(String department) {
        String sql = "SELECT * FROM employees WHERE department = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM employees WHERE salary > ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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