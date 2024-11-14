import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "database.properties";/* путь к файлу с
    с настройками БД*/
    private static Connection connection;// Объект соединения с БД

    static {//статический блок инициализации (выполняется один раз при закгрузке класс)
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(PROPERTIES_FILE));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            Class.forName("com.mysql.cj.jdbc.Driver"); // загрузка драйвера

            connection = DriverManager.getConnection(url, user, password);// объект подключения к БД

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection getConnection() {
        return connection;
    }

}


class Company {
    public static void main(String[] args) {
        HR manager = new HR();

        // Добавление нового сотрудника
        manager.addEmployee("Иван", "Иванов", "ivanov@example.com", "IT", 3000.00, Date.valueOf("2024-11-10"));
        manager.addEmployee("Владимир", "Шуневич", "ksiprus@mail.ru", "sales", 2500.50, Date.valueOf("2024-11-21"));
        manager.addEmployee("Петр", "Петров", "legasy87@gmail.com", "administration", 1500.00, Date.valueOf("2024-11-22"));
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
