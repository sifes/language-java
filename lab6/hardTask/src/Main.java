import java.util.Random;
import java.util.Scanner;

class RedBlackTree {
    private static class Node {
        int key;
        Node left, right, parent;
        boolean red; // true = RED, false = BLACK

        Node(int key) {
            this.key = key;
            this.red = true; // новий вузол завжди червоний при вставці
        }
    }

    private Node root;

    // Ліва ротація
    private void leftRotate(Node x) {
        Node y = x.right;
        if (y == null) return;

        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    // Права ротація
    private void rightRotate(Node x) {
        Node y = x.left;
        if (y == null) return;

        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }

        y.right = x;
        x.parent = y;
    }

    // Публічний метод вставки
    public void insert(int key) {
        Node newNode = new Node(key);
        bstInsert(newNode);
        fixInsert(newNode);
    }

    // Звичайна BST-вставка
    private void bstInsert(Node z) {
        Node y = null;
        Node x = root;

        while (x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else if (z.key > x.key) {
                x = x.right;
            } else {
                // Якщо вже є такий ключ – просто не дублюємо
                return;
            }
        }

        z.parent = y;
        if (y == null) {
            root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
    }

    // Виправлення форми дерева після вставки
    private void fixInsert(Node z) {
        while (z.parent != null && z.parent.red) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right; // дядько
                if (y != null && y.red) {
                    // Випадок 1: дядько червоний
                    z.parent.red = false;
                    y.red = false;
                    z.parent.parent.red = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        // Випадок 2: z – правий син
                        z = z.parent;
                        leftRotate(z);
                    }
                    // Випадок 3: z – лівий син
                    z.parent.red = false;
                    z.parent.parent.red = true;
                    rightRotate(z.parent.parent);
                }
            } else {
                // Дзеркальний випадок
                Node y = z.parent.parent.left; // дядько
                if (y != null && y.red) {
                    z.parent.red = false;
                    y.red = false;
                    z.parent.parent.red = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.red = false;
                    z.parent.parent.red = true;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.red = false; // корінь завжди чорний
    }

    // In-order обхід
    public void inorderTraversal() {
        System.out.print("In-order обхід: ");
        inorderTraversal(root);
        System.out.println();
    }

    private void inorderTraversal(Node node) {
        if (node == null) return;
        inorderTraversal(node.left);
        System.out.print(node.key + " ");
        inorderTraversal(node.right);
    }

    // Вивід дерева "боком"
    public void printTree() {
        System.out.println("Дерево (R - червоне, B - чорне):");
        printTree(root, "", true);
    }

    private void printTree(Node node, String prefix, boolean isTail) {
        if (node == null) {
            System.out.println(prefix + (isTail ? "└── " : "┌── ") + "null");
            return;
        }

        // Праве піддерево
        if (node.right != null) {
            printTree(node.right, prefix + (isTail ? "    " : "│   "), false);
        }

        // Поточний вузол
        System.out.println(prefix + (isTail ? "└── " : "┌── ")
                + node.key + (node.red ? "(R)" : "(B)"));

        // Ліве піддерево
        if (node.left != null) {
            printTree(node.left, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    // Додати масив чисел
    public void insertArray(int[] arr) {
        for (int value : arr) {
            insert(value);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RedBlackTree tree = new RedBlackTree();
        Random random = new Random();

        while (true) {
            System.out.println("\n=== Червоно-чорне дерево ===");
            System.out.println("1. Заповнити дерево випадковими числами (у довільному порядку)");
            System.out.println("2. Заповнити дерево числами з впорядкованого масиву");
            System.out.println("3. Додати число з клавіатури");
            System.out.println("4. Показати in-order обхід");
            System.out.println("5. Відобразити дерево");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": {
                    System.out.print("Скільки випадкових чисел додати? ");
                    int n = readInt(scanner);
                    int[] arr = new int[n];
                    System.out.print("Масив (довільний порядок): ");
                    for (int i = 0; i < n; i++) {
                        arr[i] = random.nextInt(100);
                        System.out.print(arr[i] + " ");
                    }
                    System.out.println();
                    tree.insertArray(arr);
                    System.out.println("Елементи додано в дерево.");
                    break;
                }
                case "2": {
                    System.out.print("Скільки випадкових чисел створити та впорядкувати? ");
                    int n = readInt(scanner);
                    int[] arr = new int[n];
                    for (int i = 0; i < n; i++) {
                        arr[i] = random.nextInt(100);
                    }
                    java.util.Arrays.sort(arr);
                    System.out.print("Впорядкований масив: ");
                    for (int value : arr) {
                        System.out.print(value + " ");
                    }
                    System.out.println();
                    tree.insertArray(arr);
                    System.out.println("Елементи додано в дерево (у порядку зростання).");
                    break;
                }
                case "3": {
                    System.out.print("Введіть ціле число: ");
                    int value = readInt(scanner);
                    tree.insert(value);
                    System.out.println("Число " + value + " додано в дерево.");
                    break;
                }
                case "4": {
                    if (tree.isEmpty()) {
                        System.out.println("Дерево порожнє.");
                    } else {
                        tree.inorderTraversal();
                    }
                    break;
                }
                case "5": {
                    if (tree.isEmpty()) {
                        System.out.println("Дерево порожнє.");
                    } else {
                        tree.printTree();
                    }
                    break;
                }
                case "0": {
                    System.out.println("Вихід з програми.");
                    scanner.close();
                    return;
                }
                default:
                    System.out.println("Невірний вибір, спробуйте ще раз.");
            }
        }
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Помилка. Введіть ціле число: ");
            }
        }
    }
}
