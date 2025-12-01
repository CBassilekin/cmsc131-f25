package examples.geometry;

public class Point {
    
    private double xVal, yVal;

    public Point(double x, double y) {
        xVal = x;
        yVal = y;
    }

    public double distance(Point other) {
        return Math.sqrt(
            Math.pow(this.xVal - other.xVal, 2) 
            + Math.pow(this.yVal - other.yVal, 2)
        );
    }

    public double getX() {
        return xVal;
    }

    public double getY() {
        return yVal;
    }

    public String toString() {
        return String.format("(%f, %f)", xVal, yVal);
    }

}
