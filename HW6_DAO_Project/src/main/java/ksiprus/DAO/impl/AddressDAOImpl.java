package ksiprus.DAO.impl;

import ksiprus.DAO.AddressDAO;
import ksiprus.DTO.AddressDTO;
import ksiprus.utilities.SQLExecutor;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDAOImpl implements AddressDAO {

    // SQL-запросы для операций с таблицей адресов
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS address (" +
                    " id INT AUTO_INCREMENT PRIMARY KEY," +
                    " street VARCHAR(50) NOT NULL," +
                    " house INT NOT NULL);";

    private static final String INSERT_QUERY =
            "INSERT INTO address (street, house) VALUES (?, ?);";

    private static final String UPDATE_QUERY =
            "UPDATE address SET street = ?, house = ? WHERE id = ?;";

    private static final String DELETE_QUERY =
            "DELETE FROM address WHERE id = ?;";

    private static final String DELETE_FIRST_QUERY =
            "DELETE FROM address LIMIT 1;";

    private static final String SELECT_QUERY =
            "SELECT * FROM address WHERE id = ?;";

    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM address;";

    private static final String INCREASE_HOUSE_QUERY =
            "UPDATE address SET house = house + 1 ORDER BY id DESC LIMIT 2;";

    @Override
    public void createTable() {
        // Создание таблицы, если она не существует
        SQLExecutor.executeSQL(CREATE_TABLE_QUERY, PreparedStatement::executeUpdate);
    }

    @Override
    public boolean save(AddressDTO address) {
        // Сохранение нового адреса в базе данных
        return Boolean.TRUE.equals(SQLExecutor.executeSQL(INSERT_QUERY, statement -> {
            statement.setString(1, address.getStreet()); // Установка значения улицы
            statement.setInt(2, address.getHouseNumber()); // Установка номера дома
            return statement.executeUpdate() > 0; // Возврат успешности операции
        }));
    }


    public AddressDTO get(Serializable id) {
        // Получение адреса по идентификатору
        return SQLExecutor.executeSQL(SELECT_QUERY, statement -> {
            statement.setInt(1, (Integer) id); // Установка идентификатора
            return getRecordAsObject(statement); // Получение адреса как объекта
        });
    }

    @Override
    public boolean update(Serializable id, AddressDTO address) {
        // Обновление информации об адресе
        return Boolean.TRUE.equals(SQLExecutor.executeSQL(UPDATE_QUERY, statement -> {
            statement.setString(1, address.getStreet()); // Установка новой улицы
            statement.setInt(2, address.getHouseNumber()); // Установка нового номера дома
            statement.setInt(3, (Integer) id); // Установка идентификатора адреса для обновления
            return statement.executeUpdate() > 0; // Возврат успешности операции
        }));
    }

    @Override
    public boolean delete(Serializable id) {
        // Удаление адреса по идентификатору
        return Boolean.TRUE.equals(SQLExecutor.executeSQL(DELETE_QUERY, statement -> {
            statement.setInt(1, (Integer) id); // Установка идентификатора для удаления
            return statement.executeUpdate() > 0; // Возврат успешности операции
        }));
    }

    @Override
    public boolean deleteFirst() {
        // Удаление первого адреса из таблицы
        return Boolean.TRUE.equals(SQLExecutor.executeSQL(DELETE_FIRST_QUERY,
                statement -> statement.executeUpdate() > 0));
    }

    @Override
    public List<AddressDTO> getAll() {
        // Получение всех адресов из таблицы
        return SQLExecutor.executeSQL(SELECT_ALL_QUERY, AddressDAOImpl::getRecordsAsList);
    }

    @Override
    public boolean incrHouseByOne() throws SQLException {
        // Увеличение номера дома для последних двух адресов
        return Boolean.TRUE.equals(SQLExecutor.executeSQL(INCREASE_HOUSE_QUERY, stmt -> stmt.executeUpdate() > 0));
    }

    // Приватный метод для преобразования результата запроса в список адресов
    private static List<AddressDTO> getRecordsAsList(PreparedStatement statement) throws SQLException {
        List<AddressDTO> list = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) { // Выполнение запроса
            while (resultSet.next()) { // Пока есть результаты
                list.add(new AddressDTO(
                        resultSet.getInt("id"), // Получение ID
                        resultSet.getString("street"), // Получение улицы
                        resultSet.getInt("house"))); // Получение номера дома
            }
        }
        return list; // Возврат списка адресов
    }

    // Приватный метод для преобразования результата запроса в один объект AddressDTO
    private static AddressDTO getRecordAsObject(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) { // Выполнение запроса
            resultSet.next(); // Переход к первой строке результата
            return new AddressDTO(
                    resultSet.getInt("id"), // Получение ID
                    resultSet.getString("street"), // Получение улицы
                    resultSet.getInt("house")); // Получение номера дома
        }
    }
}
