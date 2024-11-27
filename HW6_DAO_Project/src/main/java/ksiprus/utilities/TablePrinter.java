package ksiprus.utilities;

/**
 * Класс {@code TablePrinter} предназначен для форматированного вывода таблиц
 * на консоль. Он принимает заголовки и данные в виде массивов строк и
 * выводит их в виде красивой таблицы с правильно выровненными столбцами.
 */
public class TablePrinter {

    private String[] headers;  // Заголовки столбцов
    private String[][] data;    // Данные таблицы

    /**
     * Конструктор класса {@code TablePrinter}.
     *
     * @param headers заголовки столбцов таблицы
     * @param data данные для отображения в таблице
     */
    public TablePrinter(String[] headers, String[][] data) {
        this.headers = headers;
        this.data = data;
    }

    /**
     * Выводит таблицу на консоль.
     */
    public void printTable() {
        // Определяем максимальную ширину для каждого столбца
        int[] columnWidths = new int[headers.length];

        // Определяем ширину заголовков
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        // Определяем ширину данных, обновляя максимальную ширину столбцов
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                if (row[i].length() > columnWidths[i]) {
                    columnWidths[i] = row[i].length();
                }
            }
        }

        // Вывод заголовков таблицы
        printRow(headers, columnWidths);
        printSeparator(columnWidths);

        // Вывод строк данных
        for (String[] row : data) {
            printRow(row, columnWidths);
        }
    }

    /**
     * Выводит строку таблицы с учетом ширины столбцов.
     *
     * @param row строка таблицы (массив строк)
     * @param columnWidths массив, содержащий ширину каждого столбца
     */
    private void printRow(String[] row, int[] columnWidths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            sb.append(String.format("%-" + columnWidths[i] + "s", row[i])); // Форматирование строки
            if (i < row.length - 1) {
                sb.append(" | ");  // Разделитель между столбцами
            }
        }
        System.out.println(sb.toString()); // Вывод строки таблицы
    }

    /**
     * Выводит разделитель между заголовком и данными таблицы.
     *
     * @param columnWidths массив, содержащий ширину каждого столбца
     */
    private void printSeparator(int[] columnWidths) {
        StringBuilder sb = new StringBuilder();
        for (int width : columnWidths) {
            for (int i = 0; i < width + 2; i++) { // +2 для учета пробелов
                sb.append("-");
            }
            sb.append("+"); // Добавляет разделитель между столбцами
        }
        System.out.println(sb.toString()); // Выводит строку-разделитель
    }
}
