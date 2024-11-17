package ksiprus;

import ksiprus.DAO.AddressDAO;
import ksiprus.DAO.PeopleDAO;
import ksiprus.DAO.impl.AddressDAOImpl;
import ksiprus.DAO.impl.PeopleDAOImpl;
import ksiprus.DTO.AddressDTO;
import ksiprus.DTO.PeopleDTO;
import ksiprus.utilities.TablePrinter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс приложения.
 */
public class Main {
    private static final List<AddressDTO> addressesList = new ArrayList<>();
    private static final List<PeopleDTO> peopleList = new ArrayList<>();

    public static void main(String[] args) throws SQLException {
        fillDTOLists();  // Заполнение списков данными

        AddressDAO addressDAO = new AddressDAOImpl();
        PeopleDAO peopleDAO = new PeopleDAOImpl();

        addressDAO.createTable();
        peopleDAO.createTable();

        saveDataToDatabase(addressDAO, addressesList);
        saveDataToDatabase(peopleDAO, peopleList);

        System.out.println("Изначальный вид таблиц:");
        printAddresses(addressDAO);
        System.out.println();
        printPeople(peopleDAO);

        // Обновление данных и повторный вывод
        updateAddressData(addressDAO);
        updatePeopleData(peopleDAO);
    }

    /**
     * Метод для заполнения списков данными.
     */
    private static void fillDTOLists() {
        addressesList.add(new AddressDTO(1, "Молодежная", 5));
        addressesList.add(new AddressDTO(2, "Молодежная", 20));
        addressesList.add(new AddressDTO(3, "Интернациональная", 7));
        addressesList.add(new AddressDTO(4, "Заречная", 10));
        addressesList.add(new AddressDTO(5, "Шахтеров", 17));

        peopleList.add(new PeopleDTO(1, "Петр", "Петров", 21));
        peopleList.add(new PeopleDTO(2, "Иван", "Иванов", 25));
        peopleList.add(new PeopleDTO(3, "Сидор", "Сидоров", 35));
        peopleList.add(new PeopleDTO(4, "Александ", "Александров", 60));
        peopleList.add(new PeopleDTO(5, "Василий", "Пупкин", 43));
    }

    /**
     * Метод для сохранения данных в базе данных.
     *
     * @param dao объект DAO для работы с адресами или людьми
     * @param dataList список данных, которые нужно сохранить
     */
    private static void saveDataToDatabase(Object dao, List<?> dataList) throws SQLException {
        for (Object item : dataList) {
            if (dao instanceof AddressDAO) {
                ((AddressDAO) dao).save((AddressDTO) item);
            } else if (dao instanceof PeopleDAO) {
                ((PeopleDAO) dao).save((PeopleDTO) item);
            }
        }
    }

    /**
     * Метод для печати таблицы адресов.
     *
     * @param addressDAO объект доступа к данным для адресов
     */
    private static void printAddresses(AddressDAO addressDAO) {
        System.out.println();
        printTable(
                new String[]{"ID", "Улица", "Дом"},
                addressesList.toArray(new AddressDTO[0])
        );
    }

    /**
     * Метод для печати таблицы людей.
     *
     * @param peopleDAO объект доступа к данным для людей
     */
    private static void printPeople(PeopleDAO peopleDAO) {
        printTable(
                new String[]{"ID", "Имя", "Фамилия", "Возраст"},
                peopleList.toArray(new PeopleDTO[0])
        );
    }

    /**
     * Генерирует и выводит таблицу из данных адресов или людей.
     *
     * @param headers заголовки таблицы
     * @param data    массив данных (AddressDTO или PeopleDTO)
     */
    private static void printTable(String[] headers, Object[] data) {
        String[][] tableData = new String[data.length][headers.length];

        for (int i = 0; i < data.length; i++) {
            if (data[i] instanceof AddressDTO) {
                AddressDTO address = (AddressDTO) data[i];
                tableData[i][0] = String.valueOf(address.getId());
                tableData[i][1] = address.getStreet();
                tableData[i][2] = String.valueOf(address.getHouseNumber());
            } else if (data[i] instanceof PeopleDTO) {
                PeopleDTO person = (PeopleDTO) data[i];
                tableData[i][0] = String.valueOf(person.getId());
                tableData[i][1] = person.getName();
                tableData[i][2] = person.getSurname();
                tableData[i][3] = String.valueOf(person.getAge());
            }
        }

        TablePrinter printer = new TablePrinter(headers, tableData);
        printer.printTable();
    }

    /**
     * Метод для обновления данных таблицы адресов.
     *
     * @param addressDAO объект доступа к данным для адресов
     */
    private static void updateAddressData(AddressDAO addressDAO) {
        try {
            addressDAO.incrHouseByOne();
            addressDAO.deleteFirst();
            System.out.println();
            System.out.println("Таблица адресов после изменений:");
            List<AddressDTO> updatedAddresses = addressDAO.getAll();
            printTable(new String[]{"ID", "Улица", "Дом"}, updatedAddresses.toArray(new AddressDTO[0]));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для обновления данных таблицы людей.
     *
     * @param peopleDAO объект доступа к данным для людей
     */
    private static void updatePeopleData(PeopleDAO peopleDAO) {
        try {
            peopleDAO.deleteFirst();
            peopleDAO.increaseAgebyTwo();
            System.out.println();
            System.out.println("Таблица людей после изменений:");
            List<PeopleDTO> updatedPeople = peopleDAO.getAll();
            printTable(new String[]{"ID", "Имя", "Фамилия", "Возраст"}, updatedPeople.toArray(new PeopleDTO[0]));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
