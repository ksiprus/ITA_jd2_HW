package ksiprus.utilities;

import java.util.ResourceBundle;

/**
 * Класс {@code JDBCResources} является утилитарным классом для
 * получения данных, необходимых JDBC для доступа к базе данных.
 * <p>
 * Этот класс загружает параметры соединения с базой данных,
 * такие как URL, имя пользователя и пароль, из ресурсов.
 * </p>
 */
public class JDBCResources {

    // Имя ресурса для загрузки данных о базе данных
    private static final String DATABASE_RESOURCE = "database";

    // Ключи для извлечения информации о подключении
    private static final String URL_KEY = "url";
    private static final String USER_KEY = "user";
    private static final String PASSWORD_KEY = "password";

    // Загружаем ресурсный пакет
    private static final ResourceBundle resource = ResourceBundle.getBundle(DATABASE_RESOURCE);

    // Получение значений конфигурации
    private static final String URL = getValue(URL_KEY);
    private static final String USER = getValue(USER_KEY);
    private static final String PASSWORD = getValue(PASSWORD_KEY);

    // Метод для получения значения из ресурсного пакета по ключу
    private static String getValue(String key) {
        return resource.getString(key); // Возврат значения для указанного ключа
    }

    // Метод для получения URL базы данных
    public static String getUrl() {
        return URL; // Возврат URL
    }

    // Метод для получения пароля к базе данных
    public static String getPassword() {
        return PASSWORD; // Возврат пароля
    }

    // Метод для получения имени пользователя для подключения к базе данных
    public static String getUser() {
        return USER; // Возврат имени пользователя
    }
}
