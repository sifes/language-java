import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterReader;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class Main {

    // Фільтр-записувач: під час запису зсуває коди символів на key
    private static class ShiftWriter extends FilterWriter {
        private final int key;

        protected ShiftWriter(Writer out, char keyChar) {
            super(out);
            this.key = (int) keyChar;
        }

        @Override
        public void write(int c) throws IOException {
            super.write(c + key);
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            for (int i = off; i < off + len; i++) {
                cbuf[i] = (char) (cbuf[i] + key);
            }
            super.write(cbuf, off, len);
        }

        @Override
        public void write(String str, int off, int len) throws IOException {
            char[] buf = str.substring(off, off + len).toCharArray();
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (char) (buf[i] + key);
            }
            super.write(buf, 0, buf.length);
        }
    }

    // Фільтр-читач: під час читання віднімає key (дешифрування)
    private static class ShiftReader extends FilterReader {
        private final int key;

        protected ShiftReader(Reader in, char keyChar) {
            super(in);
            this.key = (int) keyChar;
        }

        @Override
        public int read() throws IOException {
            int c = super.read();
            if (c == -1) return -1;
            return c - key;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            int count = super.read(cbuf, off, len);
            if (count == -1) return -1;
            for (int i = off; i < off + count; i++) {
                cbuf[i] = (char) (cbuf[i] - key);
            }
            return count;
        }
    }

    // a. Шифрування файлу
    public static void encryptFile(String inputPath, String outputPath, char key) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             ShiftWriter writer = new ShiftWriter(new FileWriter(outputPath), key)) {

            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
            System.out.println("Файл успішно зашифровано.");
        } catch (IOException e) {
            System.out.println("Помилка під час шифрування: " + e.getMessage());
        }
    }

    // b. Дешифрування файлу
    public static void decryptFile(String inputPath, String outputPath, char key) {
        try (ShiftReader reader = new ShiftReader(new FileReader(inputPath), key);
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
            System.out.println("Файл успішно розшифровано.");
        } catch (IOException e) {
            System.out.println("Помилка під час дешифрування: " + e.getMessage());
        }
    }

    // Проста консольна менюшка
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Оберіть режим (1 - шифрування, 2 - дешифрування): ");
            int mode = scanner.nextInt();
            scanner.nextLine(); // з’їдаємо \n

            System.out.print("Введіть шлях до вхідного файлу: ");
            String inputPath = scanner.nextLine();

            System.out.print("Введіть шлях до вихідного файлу: ");
            String outputPath = scanner.nextLine();

            System.out.print("Введіть ключовий символ: ");
            String keyLine = scanner.nextLine();
            if (keyLine.isEmpty()) {
                System.out.println("Ключовий символ не введено.");
                return;
            }
            char key = keyLine.charAt(0);

            if (mode == 1) {
                encryptFile(inputPath, outputPath, key);
            } else if (mode == 2) {
                decryptFile(inputPath, outputPath, key);
            } else {
                System.out.println("Невідомий режим.");
            }
        } catch (Exception e) {
            System.out.println("Помилка введення: " + e.getMessage());
        }
    }
}
