package examples.geometry;

public class Rectangle {

    private Point lowerLeft, upperRight;

    public Rectangle(Point ll, Point ur) {
        // TODO data validation: check orientation of ll and ur
        lowerLeft = ll;
        upperRight = ur;
    }

    public boolean contains(Point xy) {
        return (
            // x coordinate is in range
            lowerLeft.getX() <= xy.getX() && xy.getX() <= upperRight.getX()
            // y coordinate is in range
            && lowerLeft.getY() <= xy.getY() && xy.getY() <= upperRight.getY()
        );
    }

    public double getArea() {
        double base = upperRight.getX() - lowerLeft.getX();
        double height = upperRight.getY() - lowerLeft.getY();
        return base * height;
    }

}
