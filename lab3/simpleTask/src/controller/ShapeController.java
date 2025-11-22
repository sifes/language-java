package controller;

import model.Shape;
import java.util.Arrays;
import java.util.Comparator;

public class ShapeController {

    public double totalArea(Shape[] shapes) {
        double sum = 0;
        for (Shape s : shapes) sum += s.calcArea();
        return sum;
    }

    public double totalAreaByType(Shape[] shapes, Class<?> type) {
        double sum = 0;
        for (Shape s : shapes) {
            if (type.isInstance(s)) sum += s.calcArea();
        }
        return sum;
    }

    public void sortByArea(Shape[] shapes) {
        Arrays.sort(shapes, Comparator.comparingDouble(Shape::calcArea));
    }

    public void sortByColor(Shape[] shapes) {
        Arrays.sort(shapes, Comparator.comparing(s -> s.shapeColor));
    }
}
