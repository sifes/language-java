import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class DictionaryTranslator {
    private final Map<String, String> dictionary = new HashMap<>();

    public void addWord(String english, String ukrainian) {
        if (english == null || ukrainian == null) return;
        dictionary.put(english.toLowerCase(), ukrainian);
    }

    public String translatePhrase(String phrase) {
        if (phrase == null || phrase.isEmpty()) return "";

        String[] tokens = phrase.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String token : tokens) {
            String word = token;
            String punctuation = "";

            // Відокремлюємо просту пунктуацію в кінці слова
            if (word.matches(".*[,.!?]$")) {
                punctuation = word.substring(word.length() - 1);
                word = word.substring(0, word.length() - 1);
            }

            String translated = translateWord(word);
            result.append(translated).append(punctuation).append(" ");
        }

        return result.toString().trim();
    }

    private String translateWord(String word) {
        if (word == null || word.isEmpty()) return word;
        String lower = word.toLowerCase();
        String translated = dictionary.get(lower);
        if (translated == null) {
            // Якщо слова нема в словнику – залишимо як є або позначимо
            return "[" + word + "]";
        }
        return translated;
    }

    public void printDictionary() {
        System.out.println("Поточний словник:");
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DictionaryTranslator translator = new DictionaryTranslator();

        // Початкові слова в словнику (для прикладу)
        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("i", "я");
        translator.addWord("love", "люблю");
        translator.addWord("java", "джава");

        System.out.println("=== Програма-перекладач (англ -> укр) ===");
        System.out.println("Спочатку додамо слова до словника.");
        System.out.println("Введіть пари слів у форматі: english ukrainian");
        System.out.println("Щоб зупинити додавання – введіть пустий рядок.");

        while (true) {
            System.out.print("Введіть пару слів: ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                break;
            }

            String[] parts = line.split("\\s+");
            if (parts.length < 2) {
                System.out.println("Потрібно ввести мінімум 2 слова: англійське та українське.");
                continue;
            }

            String english = parts[0];
            StringBuilder ukrainianBuilder = new StringBuilder();
            for (int i = 1; i < parts.length; i++) {
                ukrainianBuilder.append(parts[i]).append(" ");
            }
            String ukrainian = ukrainianBuilder.toString().trim();

            translator.addWord(english, ukrainian);
            System.out.println("Додано: " + english + " -> " + ukrainian);
        }

        System.out.println();
        translator.printDictionary();
        System.out.println();

        System.out.println("Тепер введіть англомовну фразу для перекладу:");
        System.out.print("> ");
        String phrase = scanner.nextLine();

        String translated = translator.translatePhrase(phrase);
        System.out.println("Переклад українською:");
        System.out.println(translated);

        scanner.close();
    }
}
