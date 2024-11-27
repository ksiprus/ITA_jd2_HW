package ksiprus.DTO;
/**
 * Класс представляет сущность адреса и создается для таблицы Address в базе данных.
 * Поля соответствуют колонкам в таблице и включают следующие атрибуты.
 */

public class AddressDTO {
    private int id;
    private String street;
    private int houseNumber;


    public AddressDTO(int id, String street, int houseNumber) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }
}
