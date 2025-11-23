import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до файлу: ");
        String filePath = scanner.nextLine();

        String maxLine = null;
        int maxWordCount = 0;

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int wordCount = countWords(line);
                if (wordCount > maxWordCount) {
                    maxWordCount = wordCount;
                    maxLine = line;
                }
            }

            if (maxLine != null) {
                System.out.println("Рядок з максимальною кількістю слів (" + maxWordCount + "):");
                System.out.println(maxLine);
            } else {
                System.out.println("Файл порожній або не містить рядків.");
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу: " + e.getMessage());
        }
    }

    private static int countWords(String line) {
        if (line == null || line.isBlank()) {
            return 0;
        }
        String[] parts = line.trim().split("\\s+");
        return parts.length;
    }
}
