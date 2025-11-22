package model;

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Rectangle...");
    }

    @Override
    public String toString() {
        return super.toString() + " (w=" + width + ", h=" + height + ")";
    }
}
