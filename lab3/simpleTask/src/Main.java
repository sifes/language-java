import controller.ShapeController;
import model.*;
import view.ShapeView;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Rectangle("Red", 4, 6),
                new Circle("Blue", 3),
                new Triangle("Green", 5, 7),
                new Circle("Red", 2),
                new Rectangle("Black", 10, 3),
                new Triangle("Blue", 8, 4),
                new Rectangle("Yellow", 2, 9),
                new Circle("Purple", 5),
                new Triangle("Red", 6, 9),
                new Rectangle("Pink", 3, 3)
        };

        ShapeView view = new ShapeView();
        ShapeController controller = new ShapeController();

        view.showAll(shapes);

        view.showResult("Total area of all shapes: ", controller.totalArea(shapes));
        view.showResult("Total area of circles: ", controller.totalAreaByType(shapes, Circle.class));

        controller.sortByArea(shapes);
        System.out.println("=== SORTED BY AREA ===");
        view.showAll(shapes);
        controller.sortByColor(shapes);
        System.out.println("=== SORTED BY COLOR ===");
        view.showAll(shapes);
    }
}
