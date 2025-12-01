package examples.canine;

public class UseCase {
    
    public static void main(String args[]) {
        Canine dog1;
        dog1 = new Canine();

        Canine dog2 = new Canine("Tom", 2.0, 3);

        Canine dog3 = new Canine("Rex", 13.1, 7);

        System.out.println(
            dog1.name + " is a dog, " + dog1.getHeight() + " units in hight."
        );

        // printMe is a "formatted string", somewhat easier to specify
        String printMe = String.format(
            "%s is %d units old.", 
            dog2.name, // string specified with %s
            dog2.age // decimal integer specified with %d
        );
        System.out.println(printMe);
        dog2.birthday(1);
        printMe = String.format(
            "%s had a birthday and is now %d units old but still %f units tall.",
            dog2.name, 
            dog2.age,
            dog2.getHeight()  // double specified by %f
        );
        System.out.println(printMe);

        // printf is convenient for immediate display
        System.out.printf("%s says: %s", dog3.name, dog3.talk());
    }

}  // end class: UseCase
