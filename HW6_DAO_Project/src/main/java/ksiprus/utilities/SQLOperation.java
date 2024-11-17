package ksiprus.utilities;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Интерфейс {@code SQLOperation} представляет функциональный интерфейс,
 * который позволяет выполнять операции с подготовленными SQL-запросами.
 *
 * <p>
 * Этот интерфейс используется в контексте выполнения SQL-запросов,
 * предоставляя метод {@code execute}, который принимает подготовленный
 * оператор и возвращает результат выполнения операции.
 * </p>
 *
 * @param <T> тип результата выполнения операции
 */
@FunctionalInterface
public interface SQLOperation<T> {
    /**
     * Выполняет операцию с использованием предоставленного подготовленного запроса.
     *
     * @param stmt подготовленный SQL-запрос, который будет использоваться для выполнения операции
     * @return результат выполнения операции
     * @throws SQLException если произойдет ошибка при выполнении SQL-запроса
     */
    T execute(PreparedStatement stmt) throws SQLException;
}
