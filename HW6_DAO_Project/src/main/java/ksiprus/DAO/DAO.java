package ksiprus.DAO;

import java.sql.SQLException;
import java.io.Serializable;
import java.util.List;

/*
* Общий интерфейс содержащий методы для доступа к базе данных
* Вместо параметра (T) устанавливается объект типа DTO, соответствующей таблицы
* */

public interface DAO<T> {

    // метод для создания таблицы
    void createTable() throws SQLException;

    // метод для сохранения данных в таблицу
    boolean save(T t) throws SQLException;

    // метод получения одного объекта по id (идентификатор объекта)
    T get(Serializable id) throws SQLException;

    // метод для обновления записи в таблице
    boolean update(Serializable id, T t) throws SQLException;

    // метод для удаления объекта по id
    boolean delete(Serializable id) throws SQLException;

    // метод для удаления первой записи из таблицы
    boolean deleteFirst() throws SQLException;

    //метод выводящий все записи таблицы
    List<T> getAll() throws SQLException;
}
