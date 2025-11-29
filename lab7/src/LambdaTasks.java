import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LambdaTasks {

    // -------------------------
    // 1. Рядки з довжиною < / > середньої
    // -------------------------

    public static String[] stringsShorterThanAverage(String[] input) {
        double avg = Arrays.stream(input)
                .mapToInt(String::length)
                .average()
                .orElse(0);

        return Arrays.stream(input)
                .filter(s -> s.length() < avg)
                .toArray(String[]::new);
    }

    public static String[] stringsLongerThanAverage(String[] input) {
        double avg = Arrays.stream(input)
                .mapToInt(String::length)
                .average()
                .orElse(0);

        return Arrays.stream(input)
                .filter(s -> s.length() > avg)
                .toArray(String[]::new);
    }

    // -------------------------
    // 2. Слово з мінімальною кількістю різних символів
    // -------------------------

    public static String wordWithMinDistinctChars(String text) {
        return Arrays.stream(splitToWords(text))
                .min(Comparator.comparingInt(LambdaTasks::distinctCharCount))
                .orElse(null);
    }

    private static int distinctCharCount(String s) {
        return (int) s.chars().distinct().count();
    }

    // -------------------------
    // 3. Слова тільки з латинських літер
    //    + рівна кількість голосних та приголосних
    // -------------------------

    public static String[] latinWordsWithEqualVowelsAndConsonants(String text) {
        return Arrays.stream(splitToWords(text))
                .filter(w -> w.matches("[A-Za-z]+"))
                .filter(LambdaTasks::hasEqualVowelsAndConsonants)
                .toArray(String[]::new);
    }

    private static boolean hasEqualVowelsAndConsonants(String word) {
        String vowels = "aeiouAEIOU";
        long vowelCount = word.chars()
                .filter(c -> vowels.indexOf(c) >= 0)
                .count();
        long consonantCount = word.chars()
                .filter(Character::isLetter)
                .filter(c -> vowels.indexOf(c) < 0)
                .count();
        return vowelCount == consonantCount && vowelCount > 0;
    }

    // -------------------------
    // 4. Слова, де символи йдуть в порядку зростання їх кодів
    // -------------------------

    public static String[] wordsWithIncreasingCharCodes(String text) {
        return Arrays.stream(splitToWords(text))
                .filter(LambdaTasks::isStrictlyIncreasingByCode)
                .toArray(String[]::new);
    }

    private static boolean isStrictlyIncreasingByCode(String word) {
        char[] chars = word.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] <= chars[i - 1]) {
                return false;
            }
        }
        return chars.length > 0;
    }

    // -------------------------
    // 5. Слова тільки з різних символів
    // -------------------------

    public static String[] wordsWithAllDistinctChars(String text) {
        return Arrays.stream(splitToWords(text))
                .filter(LambdaTasks::allCharsDistinct)
                .toArray(String[]::new);
    }

    private static boolean allCharsDistinct(String word) {
        return word.chars().distinct().count() == word.length();
    }

    // -------------------------
    // 6. Серед простих ≤ n — з максимальною кількістю '1' в двійковому записі
    // -------------------------

    public static Integer primeWithMaxOnesInBinary(int n) {
        return IntStream.rangeClosed(2, n)
                .filter(LambdaTasks::isPrime)
                .boxed()
                .max(Comparator.comparingInt(p -> Integer.bitCount(p)))
                .orElse(null);
    }

    // -------------------------
    // 7. Серед простих ≤ n — з максимальною кількістю '0' в двійковому записі
    // -------------------------

    public static Integer primeWithMaxZerosInBinary(int n) {
        return IntStream.rangeClosed(2, n)
                .filter(LambdaTasks::isPrime)
                .boxed()
                .max(Comparator.comparingInt(LambdaTasks::zeroCountInBinary))
                .orElse(null);
    }

    private static int zeroCountInBinary(int value) {
        String bin = Integer.toBinaryString(value);
        long ones = bin.chars().filter(c -> c == '1').count();
        return (int) (bin.length() - ones);
    }

    // -------------------------
    // 8. Кількість надпростих чисел (prime + reverse(prime) теж prime)
    // -------------------------

    public static long countSuperPrimes(int n) {
        return IntStream.rangeClosed(2, n)
                .filter(LambdaTasks::isSuperPrime)
                .count();
    }

    private static boolean isSuperPrime(int x) {
        if (!isPrime(x)) return false;
        int reversed = reverseDigits(x);
        return isPrime(reversed);
    }

    private static int reverseDigits(int x) {
        int res = 0;
        while (x > 0) {
            res = res * 10 + x % 10;
            x /= 10;
        }
        return res;
    }

    // -------------------------
    // 9. Усі досконалі числа в [1; n]
    // -------------------------

    public static List<Integer> perfectNumbersUpTo(int n) {
        return IntStream.rangeClosed(1, n)
                .filter(LambdaTasks::isPerfect)
                .boxed()
                .collect(Collectors.toList());
    }

    private static boolean isPerfect(int x) {
        if (x <= 1) return false;
        int sum = 1; // 1 завжди дільник
        int limit = (int) Math.sqrt(x);
        for (int i = 2; i <= limit; i++) {
            if (x % i == 0) {
                sum += i;
                int other = x / i;
                if (other != i) {
                    sum += other;
                }
            }
        }
        return sum == x;
    }

    // -------------------------
    // Допоміжні методи
    // -------------------------

    private static String[] splitToWords(String text) {
        if (text == null || text.isBlank()) return new String[0];
        return text.trim().split("\\s+");
    }

    private static boolean isPrime(int x) {
        if (x < 2) return false;
        if (x == 2 || x == 3) return true;
        if (x % 2 == 0) return false;
        int limit = (int) Math.sqrt(x);
        for (int i = 3; i <= limit; i += 2) {
            if (x % i == 0) return false;
        }
        return true;
    }

    // -------------------------
    // Демонстрація (опціонально)
    // -------------------------

    public static void main(String[] args) {
        String[] arr = {"hello", "hi", "world", "java", "lambda"};
        System.out.println("Shorter than avg: " +
                Arrays.toString(stringsShorterThanAverage(arr)));
        System.out.println("Longer than avg: " +
                Arrays.toString(stringsLongerThanAverage(arr)));

        String text = "Hello abcd aabbcc abc xyz aEiO";
        System.out.println("Min distinct chars: " + wordWithMinDistinctChars(text));
        System.out.println("Latin equal vowels/consonants: " +
                Arrays.toString(latinWordsWithEqualVowelsAndConsonants(text)));
        System.out.println("Increasing char codes: " +
                Arrays.toString(wordsWithIncreasingCharCodes(text)));
        System.out.println("All distinct chars: " +
                Arrays.toString(wordsWithAllDistinctChars(text)));

        int n = 100;
        System.out.println("Prime with max ones in binary ≤ " + n + ": " +
                primeWithMaxOnesInBinary(n));
        System.out.println("Prime with max zeros in binary ≤ " + n + ": " +
                primeWithMaxZerosInBinary(n));
        System.out.println("Super primes count ≤ " + n + ": " +
                countSuperPrimes(n));
        System.out.println("Perfect numbers ≤ " + n + ": " +
                perfectNumbersUpTo(n));
    }
}
