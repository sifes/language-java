import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

public class HotReloader {

    private static final String CLASS_NAME = "TestModule";
    private static final String JAVA_FILE = "TestModule.java";
    private static final String CLASS_FILE = "TestModule.class";

    public static void main(String[] args) throws Exception {
        File javaFile = new File(JAVA_FILE);
        if (!javaFile.exists()) {
            System.out.println("Файл TestModule.java не знайдено. Поклади його в цю ж директорію.");
            return;
        }

        long lastModified = 0;

        System.out.println("Починаємо спостереження за " + javaFile.getAbsolutePath());
        System.out.println("Змініть текст у TestModule.java, збережіть — клас буде перекомпільовано і перевантажено.");
        System.out.println("Натисніть Ctrl+C, щоб завершити.\n");

        while (true) {
            if (javaFile.lastModified() > lastModified) {
                lastModified = javaFile.lastModified();

                if (!compile(javaFile)) {
                    System.out.println("Помилка компіляції, дивіться повідомлення вище.\n");
                } else {
                    loadAndPrint();
                }
            }

            Thread.sleep(1000); // перевіряємо раз на секунду
        }
    }

    private static boolean compile(File javaFile) {
        System.out.println("===> Виявлено зміни, компілюємо TestModule.java...");

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.out.println("SystemJavaCompiler недоступний.");
            System.out.println("Запускайте програму з JDK, а не тільки JRE.");
            return false;
        }

        int result = compiler.run(null, null, null, javaFile.getPath());
        if (result == 0) {
            System.out.println("Компіляція успішна.");
            return true;
        } else {
            System.out.println("Компіляція завершилась з помилкою, код: " + result);
            return false;
        }
    }

    private static void loadAndPrint() {
        try {
            File classFile = new File(CLASS_FILE);
            if (!classFile.exists()) {
                System.out.println("Файл TestModule.class не знайдено, пропускаю завантаження.");
                return;
            }

            // Кожного разу створюємо НОВИЙ ClassLoader -> нова версія класу
            ModuleClassLoader loader =
                    new ModuleClassLoader(classFile.getParentFile());

            Class<?> moduleClass = loader.loadClass(CLASS_NAME);
            Object moduleInstance = moduleClass.getDeclaredConstructor().newInstance();

            System.out.println("Нова версія TestModule: " + moduleInstance);
        } catch (Exception e) {
            System.out.println("Помилка під час завантаження класу:");
            e.printStackTrace();
        }
    }
}
