package ksiprus.DAO;

import ksiprus.DTO.PeopleDTO;

import java.sql.SQLException;
/*Интерфейс, который содержит в себе все методы для управления базой данных
 * и реализует метод увеличения возраста на два у двух последних человек */

public interface PeopleDAO extends DAO<PeopleDTO> {

    boolean increaseAgebyTwo() throws SQLException;
}
