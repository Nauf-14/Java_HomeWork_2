// Дана json строка вида:
// String json = "[{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"}," +
// "{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"}," +
// "{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}]";

// Задача написать метод(ы), который распарсить строку и выдаст ответ вида:
// Студент Иванов получил 5 по предмету Математика.
// Студент Петрова получил 4 по предмету Информатика.
// Студент Краснов получил 5 по предмету Физика.

// Используйте StringBuilder для подготовки ответа. Далее создайте метод, который запишет
// результат работы в файл. Обработайте исключения и запишите ошибки в лог файл с помощью Logger.

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter; 


public class Main_task_1 {
    private static final Logger logger = Logger.getLogger(Main_task_1 .class.getName());

    public static void main(String[] args) {
        String json = "[{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"}," +
                "{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"}," +
                "{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}]";

        StringBuilder result = parseJson(json);
        System.out.println(result);

        String filename = "output.txt";
        saveResultToFile(result.toString(), filename);
    }

    private static StringBuilder parseJson(String json) {
        StringBuilder result = new StringBuilder();

        try {
            json = json.replace("[", "").replace("]", "");

            String[] entries = json.split("\\},\\{");
            for (String entry : entries) {
                entry = entry.replaceAll("[{}\"]", "");

                String[] fields = entry.split(",");
                String surname = "";
                String grade = "";
                String subject = "";

                for (String field : fields) {
                    String[] keyValue = field.split(":");
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    switch (key) {
                        case "фамилия":
                            surname = value;
                            break;
                        case "оценка":
                            grade = value;
                            break;
                        case "предмет":
                            subject = value;
                            break;
                    }
                }

                result.append("Студент ").append(surname)
                        .append(" получил ").append(grade)
                        .append(" по предмету ").append(subject)
                        .append(".\n");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing JSON", e);
        }

        return result;
    }

    private static void saveResultToFile(String result, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving result to file", e);
        }
    }

    static {
        try {
            // Создание файла лога
            FileHandler fileHandler = new FileHandler("log.txt");
            fileHandler.setFormatter(new SimpleFormatter());

            // Добавление обработчика логов к логгеру
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error creating log file", e);
        }
    }
    
}
