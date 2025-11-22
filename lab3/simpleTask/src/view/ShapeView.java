package view;

import model.Shape;

public class ShapeView {

    public void showAll(Shape[] shapes) {
        System.out.println("=== ALL SHAPES ===");
        for (Shape s : shapes) {
            System.out.println(s);
        }
        System.out.println();
    }

    public void showResult(String message, double value) {
        System.out.println(message + value);
    }
}
