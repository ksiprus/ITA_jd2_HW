package ksiprus.DAO.impl;

import ksiprus.DAO.PeopleDAO;
import ksiprus.DTO.PeopleDTO;
import ksiprus.utilities.SQLExecutor;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PeopleDAOImpl implements PeopleDAO {
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS people (" +
                    " id INT AUTO_INCREMENT PRIMARY KEY," +
                    " name VARCHAR(50) NOT NULL," +
                    " surname VARCHAR(50) NOT NULL," +
                    " age INT NOT NULL);";
    private static final String INSERT_QUERY =
            "INSERT INTO people (name, surname, age)" +
                    " VALUES (?, ?, ?);";
    private static final String UPDATE_QUERY =
            "UPDATE people" +
                    " SET name = ?, surname = ?, age = ?" +
                    " WHERE id = ?;";
    private static final String DELETE_QUERY =
            "DELETE FROM people" +
                    " WHERE id = ?;";
    private static final String DELETE_FIRST_QUERY =
            "DELETE FROM people" +
                    " LIMIT 1;";
    private static final String SELECT_QUERY =
            "SELECT * FROM people" +
                    " WHERE id = ?;";
    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM people;";
    private static final String INCREASE_AGE_QUERY =
            "UPDATE people" +
                    " SET age = age + 2" +
                    " ORDER BY id DESC" +
                    " LIMIT 2;";


    @Override
    public void createTable() throws SQLException {
        SQLExecutor.executeSQL(CREATE_TABLE_QUERY, PreparedStatement::execute);
    }

    @Override
    public boolean save(PeopleDTO peopleDTO) throws SQLException {
        return Boolean.TRUE.equals(SQLExecutor.executeSQL(INSERT_QUERY, stmt -> {
            stmt.setString(1, peopleDTO.getName());
            stmt.setString(2, peopleDTO.getSurname());
            stmt.setInt(3, peopleDTO.getAge());
            return stmt.executeUpdate() > 0;

        }));
    }


   
    public PeopleDTO get(Serializable id) throws SQLException {
        return SQLExecutor.executeSQL(SELECT_QUERY, stmt -> {
            stmt.setInt(1, (int) id);
            return getRecordAsObject(stmt);
        });
    }

    @Override
    public boolean update(Serializable id, PeopleDTO peopleDTO) throws SQLException {
        return Boolean.TRUE.equals(SQLExecutor.executeSQL(UPDATE_QUERY, stmt -> {
            stmt.setInt(1, (int) id);
            stmt.setString(2, peopleDTO.getName());
            stmt.setString(3, peopleDTO.getSurname());
            stmt.setInt(4, peopleDTO.getAge());
            return stmt.executeUpdate() > 0;
        }));
    }

    @Override
    public boolean delete(Serializable id) throws SQLException {
        return Boolean.TRUE.equals(SQLExecutor.executeSQL(DELETE_QUERY, stmt -> {
            stmt.setInt(1, (int) id);
            return stmt.executeUpdate() > 0;
        }));
    }

    @Override
    public boolean deleteFirst() throws SQLException {
        return Boolean.TRUE.equals(SQLExecutor.executeSQL(DELETE_FIRST_QUERY, stmt -> {
            return stmt.executeUpdate() > 0;
        }));
    }

    private static List<PeopleDTO> getRecordsAsList(PreparedStatement statement) throws SQLException {
        List<PeopleDTO> list = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                list.add(new PeopleDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age")));
            }
        }
        return list;
    }

    @Override
    public List<PeopleDTO> getAll() throws SQLException {
        return SQLExecutor.executeSQL(SELECT_ALL_QUERY, PeopleDAOImpl::getRecordsAsList);
    }

    @Override
    public boolean increaseAgebyTwo() throws SQLException {
        return Boolean.TRUE.equals(SQLExecutor.executeSQL(INCREASE_AGE_QUERY, PreparedStatement::executeUpdate));
    }


    private static PeopleDTO getRecordAsObject(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return new PeopleDTO(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getInt("age"));
        }
    }
}
