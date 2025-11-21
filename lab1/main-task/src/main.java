import java.util.Scanner;

public class Main {

    /**
     * Перевірка, чи є число простим
     * @param num число для перевірки
     * @return true, якщо число просте
     */
    public static boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        // Перевіряємо дільники до кореня з num
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Підрахунок кількості нулів у двійковому представленні числа
     * @param num число для аналізу
     * @return кількість нулів у двійковій формі
     */
    public static int countZerosInBinary(int num) {
        String binary = Integer.toBinaryString(num);
        int zeros = 0;
        for (char c : binary.toCharArray()) {
            if (c == '0') {
                zeros++;
            }
        }
        return zeros;
    }

    /**
     * Знаходження простого числа з максимальною кількістю нулів у двійковій формі
     * @param n верхня межа діапазону
     * @return просте число з максимальною кількістю нулів
     */
    public static int findPrimeWithMaxZeros(int n) {
        int maxZeros = 0;
        int resultPrime = 2; // за замовчуванням перше просте число

        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                int zeros = countZerosInBinary(i);
                if (zeros > maxZeros) {
                    maxZeros = zeros;
                    resultPrime = i;
                }
            }
        }

        return resultPrime;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Пошук простого числа з максимальною кількістю нулів у двійковій формі ===");
        System.out.print("Введіть n (верхню межу діапазону): ");

        int n = scanner.nextInt();

        if (n < 2) {
            System.out.println("Помилка: n повинно бути не менше 2");
            scanner.close();
            return;
        }

        int result = findPrimeWithMaxZeros(n);
        String binary = Integer.toBinaryString(result);
        int zerosCount = countZerosInBinary(result);

        System.out.println("\n--- Результат ---");
        System.out.println("Просте число з максимальною кількістю нулів: " + result);
        System.out.println("Двійкове представлення: " + binary);
        System.out.println("Кількість нулів: " + zerosCount);

        // Додаткова інформація: виводимо всі прості числа з їх двійковим представленням
        System.out.println("\n--- Усі прості числа до " + n + " ---");
        System.out.printf("%-10s %-20s %-10s%n", "Число", "Двійкова форма", "Кіл-ть 0");
        System.out.println("-".repeat(45));

        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                String bin = Integer.toBinaryString(i);
                int zeros = countZerosInBinary(i);
                System.out.printf("%-10d %-20s %-10d%n", i, bin, zeros);
            }
        }

        scanner.close();
    }
}