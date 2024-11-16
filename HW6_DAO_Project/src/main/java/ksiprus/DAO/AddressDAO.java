package ksiprus.DAO;

import ksiprus.DTO.AddressDTO;

import java.sql.SQLException;

// интерфейс содержит все методы для управления базой данных и реализует собственный
public interface AddressDAO extends DAO<AddressDTO> {

    // метод увеличения номера дома на 1
    boolean incrHouseByOne() throws SQLException;
}
