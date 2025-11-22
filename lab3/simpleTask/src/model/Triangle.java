package model;

public class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(String color, double base, double height) {
        super(color);
        this.base = base;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return (base * height) / 2.0;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Triangle...");
    }

    @Override
    public String toString() {
        return super.toString() + " (base=" + base + ", height=" + height + ")";
    }
}
