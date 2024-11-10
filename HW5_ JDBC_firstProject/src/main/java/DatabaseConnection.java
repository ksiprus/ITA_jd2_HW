import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/OnlyMyShems";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static class Company {
        public static void main(String[] args) {
            HR manager = new HR();

            // Добавление нового сотрудника
            manager.addEmployee("Иван", "Иванов", "ivanov@example.com", "IT", 3000.00, Date.valueOf("2024-11-10"));
            manager.addEmployee("Владимир", "Шуневич", "ksiprus@mail.ru", "sales", 2500.50, Date.valueOf("2024-11-21"));
            manager.addEmployee("Петр","Петров","legasy87@gmail.com","administration",1500.00, Date.valueOf("2024-11-22"));
            // Изменение данных сотрудника
            manager.updateEmployee(15, "Иван", "Иванович", "ivanov@example.com", "IT", 5000.00, Date.valueOf("2024-11-10"));

            // Удаление сотрудника
            manager.deleteEmployee(15);

            // Получение всех сотрудников
            manager.getAllEmployees();

            // Поиск сотрудников по отделу
            manager.findEmployeesByDepartment("IT");

            // Получение информации о сотрудниках с заработной платой выше заданной
            manager.findEmployeesWithSalaryAbove(2000.00);
        }
    }
}