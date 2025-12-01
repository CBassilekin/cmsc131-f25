package examples.geometry;

public class Circle {

    private Point center;
    private double radius;

    public Circle(Point c, double r) {
        center = c;
        radius = r;
    }

    public boolean contains(Point xy) {
        return center.distance(xy) <= radius;
    }

    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

}
