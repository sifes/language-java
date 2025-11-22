import com.google.gson.Gson;
import java.util.Objects;

/**
 * Завдання 2.1: Person з equals та JSON серіалізацією
 * Все в одному файлі для простоти запуску
 */
public class SimpleTask {

    public static void main(String[] args) {
        System.out.println("=== Завдання 2.1: Person з equals та JSON ===\n");

        Gson gson = new Gson();

        // a. Створіть екземпляр Person
        Person originalPerson = new Person("Шевченко", "Тарас", 25);
        System.out.println("1. Оригінальний об'єкт:");
        System.out.println("   " + originalPerson);

        // b. Конвертуйте його в JSON
        String json = gson.toJson(originalPerson);
        System.out.println("\n2. JSON представлення:");
        System.out.println("   " + json);

        // c. Конвертуйте назад в об'єкт
        Person deserializedPerson = gson.fromJson(json, Person.class);
        System.out.println("\n3. Десеріалізований об'єкт:");
        System.out.println("   " + deserializedPerson);

        // d. Перевірте equals-ом початковий і одержаний об'єкти
        boolean areEqual = originalPerson.equals(deserializedPerson);
        System.out.println("\n4. Перевірка equals:");
        System.out.println("   Об'єкти рівні: " + areEqual);
        System.out.println("   Hash коди: " + originalPerson.hashCode() + " == " + deserializedPerson.hashCode());

        // Додаткові тести equals
        System.out.println("\n5. Додаткові перевірки equals:");
        Person person1 = new Person("Іванов", "Іван", 30);
        Person person2 = new Person("Іванов", "Іван", 30);
        Person person3 = new Person("Петров", "Петро", 25);

        System.out.println("   person1.equals(person2): " + person1.equals(person2));
        System.out.println("   person1.equals(person3): " + person1.equals(person3));
        System.out.println("   person1.equals(null): " + person1.equals(null));
        System.out.println("   person1.equals(person1): " + person1.equals(person1));
    }

    /**
     * Клас Person з реалізацією equals та hashCode
     */
    static class Person {
        private String lastName;  // прізвище
        private String firstName; // ім'я
        private int age;          // вік

        public Person() {
        }

        public Person(String lastName, String firstName, int age) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.age = age;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        /**
         * Реалізація методу equals згідно з контрактом Java
         */
        @Override
        public boolean equals(Object obj) {
            // Перевірка на той самий об'єкт
            if (this == obj) {
                return true;
            }
            // Перевірка на null та тип
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            // Приведення типу та порівняння полів
            Person person = (Person) obj;
            return age == person.age &&
                    Objects.equals(lastName, person.lastName) &&
                    Objects.equals(firstName, person.firstName);
        }

        /**
         * Реалізація hashCode (обов'язково при перевизначенні equals)
         */
        @Override
        public int hashCode() {
            return Objects.hash(lastName, firstName, age);
        }

        @Override
        public String toString() {
            return "Person{lastName='" + lastName + "', firstName='" + firstName + "', age=" + age + "}";
        }
    }
}
