import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static Map<String, Integer> countTags(String urlString) throws IOException {
        URL url = new URL(urlString);
        StringBuilder html = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line).append('\n');
            }
        }

        // Шукаємо <tag ...> та </tag ...>
        Pattern pattern = Pattern.compile("<\\s*/?\\s*([a-zA-Z][a-zA-Z0-9]*)[^>]*>");
        Matcher matcher = pattern.matcher(html);

        Map<String, Integer> counts = new HashMap<>();

        while (matcher.find()) {
            String tagName = matcher.group(1).toLowerCase();
            counts.put(tagName, counts.getOrDefault(tagName, 0) + 1);
        }

        return counts;
    }

    // a) сортування за назвою тегу
    private static void printSortedByTagName(Map<String, Integer> counts) {
        System.out.println("Сортування за назвою тегу (лексикографічно):");
        counts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("%s : %d%n", e.getKey(), e.getValue()));
    }

    // b) сортування за частотою
    private static void printSortedByFrequency(Map<String, Integer> counts) {
        System.out.println("\nСортування за частотою появи тегів:");
        counts.entrySet().stream()
                .sorted((e1, e2) -> {
                    int cmp = Integer.compare(e1.getValue(), e2.getValue());
                    if (cmp != 0) return cmp;
                    return e1.getKey().compareTo(e2.getKey());
                })
                .forEach(e -> System.out.printf("%s : %d%n", e.getKey(), e.getValue()));
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введіть URL сторінки (наприклад, https://example.com): ");
            String url = scanner.nextLine();

            Map<String, Integer> counts = countTags(url);

            if (counts.isEmpty()) {
                System.out.println("На сторінці не знайдено тегів або сторінка порожня.");
            } else {
                printSortedByTagName(counts);
                printSortedByFrequency(counts);
            }
        } catch (MalformedURLException e) {
            System.out.println("Некоректний URL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Помилка під час зчитування сторінки: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неочікувана помилка: " + e.getMessage());
        }
    }
}
