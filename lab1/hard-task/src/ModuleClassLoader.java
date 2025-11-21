import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ModuleClassLoader extends ClassLoader {

    private final File dir;

    public ModuleClassLoader(File dir) {
        // можна лишити parent за замовчуванням
        super(ModuleClassLoader.class.getClassLoader());
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // Сюди ми вже потрапляємо, коли вирішили завантажувати самі
        File classFile = new File(dir, name + ".class");
        byte[] bytes = loadBytes(classFile);
        return defineClass(name, bytes, 0, bytes.length);
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // Для TestModule не делегуємо батьку, а вантажимо самі
        if ("TestModule".equals(name)) {
            Class<?> c = findClass(name);
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
        // Для всього іншого — стандартна поведінка
        return super.loadClass(name, resolve);
    }

    private byte[] loadBytes(File classFile) throws ClassNotFoundException {
        try (FileInputStream in = new FileInputStream(classFile)) {
            byte[] bytes = new byte[(int) classFile.length()];
            int read = in.read(bytes);
            if (read != bytes.length) {
                throw new IOException("Не вдалося прочитати повністю class-файл.");
            }
            return bytes;
        } catch (IOException e) {
            throw new ClassNotFoundException("Не вдалося завантажити клас з файлу " + classFile, e);
        }
    }
}
