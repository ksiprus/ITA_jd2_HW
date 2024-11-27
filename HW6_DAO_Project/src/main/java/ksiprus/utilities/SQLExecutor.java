package ksiprus.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс {@code SQLExecutor} предоставляет утилиту для выполнения SQL-запросов
 * с использованием переданных операций.
 * Этот класс использует шаблон метод для выполнения SQL-операций,
 * передавая подготовленный запрос и обеспечивая автоматическое управление
 * соединениями с базой данных.
 */
public class SQLExecutor {

    /**
     * Выполняет SQL-запрос с использованием предоставленной операции.
     *
     * @param sql       строка SQL-запроса, который нужно выполнить
     * @param operation операция, которая будет выполнена с подготовленным заявлением
     * @return результат выполнения операции
     */
    public static <T> T executeSQL(String sql, SQLOperation<T> operation) {
        // Попытка открыть соединение и выполнить операцию
        try (Connection connection = DriverManager.getConnection(
                JDBCResources.getUrl(),
                JDBCResources.getUser(),
                JDBCResources.getPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Выполнение операции и возврат результата
            return operation.execute(statement);
        } catch (SQLException e) {
            // Обработка исключений при выполнении SQL-запроса
            System.out.println("Возникла ошибка при выполнении SQL запроса! " +
                    "Код ошибки: " + e.getSQLState() + " " +
                    e.getLocalizedMessage());
            return null; // Возвращаем null в случае ошибки
        }
    }
}
