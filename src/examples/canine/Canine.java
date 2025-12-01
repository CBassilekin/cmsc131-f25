package examples.canine;

public class Canine {
    static int population = (int) 7.0e8;  // cast specified value
    public String name;
    private double height;
    public int age;

    /** 
     * Construct a new Canine with default values 
     * name = "Dog", height = 1.0, age = 0
     * 
    */
    public Canine() {
        name = "Dog";
        height = 1.0;
        age = 0;
        population = population + 1;
    }

    /** 
     * Construct a Canine with provided values
     * 
     * @param name Name of this dog.
     * 
     * @param height Height of this dog, must be positive.
     * 
     * @param age Age of this dog, must be non-negative.
     * 
     * @throws IllegalrgumentException If height <=0 or age < 0.
    */
    public Canine(String name, double height, int age) {
        if (height <= 0) {
            throw new IllegalArgumentException(
                "height must be a positive double."
            );
        }
        if (age < 0) {
            throw new IllegalArgumentException(
                "age must be a non-negaive integer."
            );
        }
        this.name = name;  // use `this` keyword to avoid name collision
                           // or rename the arguments, eg n, h, a
        this.height = height;
        this.age = age;
        population = population + 1;
    }

    /** 
     * Set value of this dog's height.
     * 
     * @param newHeight New height value, must be positive.
     * 
     * @throws IllegalArgumentException If newHight <= 0.
    */
    public void setHeight(double newHeight) {
        if (newHeight <= 0) {
            throw new IllegalArgumentException(
                "newHeight must be a positive double."
            );
        }
        height = newHeight;
    }

    /**
     * Accessor for height attribute.
     * @return Height of this dog.
     */
    public double getHeight() {
        return height;
    }

    public String talk() {
        return "ROOFF";
    }

    /**
     * Increase this dog's age by the provided amount.
     * 
     * @param deltaAge Amount of increase, must be positive.
     */
    public void birthday(int deltaAge) {
        if (deltaAge <= 0) {
            throw new IllegalArgumentException(
                "deltaAge must be a positive integer."
            );
        }
        age = age + deltaAge;
    }

}  // end: Canine class
