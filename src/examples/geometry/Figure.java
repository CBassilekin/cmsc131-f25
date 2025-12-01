package examples.geometry;

import java.util.Random;

class Figure {

    private final Rectangle rectangleA, rectangleB;
    private final Circle circleX, circleY;

    public Figure(
        Rectangle rectA, 
        Rectangle rectB, 
        Circle circX, 
        Circle circY
    ){
        rectangleA = rectA;
        rectangleB = rectB;
        circleX = circX;
        circleY = circY;
    }

    public boolean contains(Point testPoint){
        // logic: testPoint in (A or B) and not X and not Y
        return (
                (
                    rectangleA.contains(testPoint) 
                    || rectangleB.contains(testPoint)
                )
                && !circleX.contains(testPoint)
                && !circleY.contains(testPoint)
            );
    }

    public static void main(String[] args) {
        // set up components of figure
        Point llA = new Point(2.0, 3.0);
        Point urA = new Point(24.0, 19.0);
        Rectangle rectA = new Rectangle(llA, urA);

        Point llB = new Point(7.0, -5.0);
        Point urB = new Point(18.0, 3.0);
        Rectangle rectB = new Rectangle(llB, urB);

        Point cX = new Point(11.0, 1.0);
        double rX = 3.0;
        Circle circX = new Circle(cX, rX);

        Point cY = new Point(17.0, 12.0);
        double rY = 6.0;
        Circle circY = new Circle(cY, rY);

        Figure figure = new Figure(rectA, rectB, circX, circY);

        // sanity checks
        Point definitelyInside = new Point(5.0, 5.0);
        Point definitelyOutside = new Point(0.0, 0.0);
        System.out.println(String.format(
            "Point definitelyInside in figure? %b", 
            figure.contains(definitelyInside)
        ));
        System.out.println(String.format(
            "Point definitelyOutside in figure? %b",
            figure.contains(definitelyOutside)
        ));

        // calculate figure area
        double figureArea = (
            figure.rectangleA.getArea()
            + figure.rectangleB.getArea()
            - figure.circleX.getArea()
            - figure.circleY.getArea()
        );
        
        Rectangle boundingBox = new Rectangle(
            new Point(2.0, -5.0),
            new Point(24.0, 19.0)
        );

        // Monte Carlo estimation of Figure area
        // sample 100k points uniformly at random from bounding box
        Random rng = new Random(42);  // *always* seed your random generators
        int countInFigure = 0;
        double randomX, randomY;
        Point randomPoint;
        int sampleSize = 100000;
        for (int idx = 0; idx < sampleSize; idx++) {
            randomX = rng.nextDouble(2.0, 24.0);
            randomY = rng.nextDouble(-5.0, 19.0);
            randomPoint = new Point(randomX, randomY);
            if (figure.contains(randomPoint)) {
                countInFigure++;
            }
        }
        // cast to avoid integer division
        double propBoundingBoxMonteCarlo = (double) countInFigure / sampleSize;
        double figureAreaMonteCarlo = (
            propBoundingBoxMonteCarlo * boundingBox.getArea()
        );

        System.out.println(String.format(
            "(Exact) Figure covers %f percent of bounding box",
            100.0 * figureArea / boundingBox.getArea()
        ));
        System.out.println(String.format(
            "(Exact) Figure area = %f", 
            figureArea
        ));
        System.out.println(String.format(
            "(Monte Carlo): Figure covers %f percent of bounding box",
            100.0 * propBoundingBoxMonteCarlo
        ));
        System.out.println(String.format(
            "(Monte Carlo): Figure area = %f",
            figureAreaMonteCarlo
        ));
    } // end: main

} // end: Figure class
