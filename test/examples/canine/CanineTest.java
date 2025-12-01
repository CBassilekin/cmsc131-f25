package examples.canine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CanineTest {

    private Canine dog;

    /**
     * This method is run before each test. 
     * We only have to declare this Canine once, and it will be available for any test to use.
     * If any test mutates dog's state, that mutation won't carry over to other tests.
     */
    @BeforeEach
    void setUp() {
        dog = new Canine("Rex", 13.1, 7);
    }

    @Test
    void testDefaultValues() {
        Canine dog = new Canine();
        assertEquals("Dog", dog.name);
        assertEquals(1.0, dog.getHeight());
        assertEquals(0, dog.age, 0);
    }

    @Test 
    void constructorThrowsForInvalidHeight() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> {new Canine("Rex", -1, 0);}
        );
        assertEquals(
            "height must be a positive double.", 
            exception.getMessage()
        );
    }

    @Test
    void constructorThrowsForInvalidAge() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> {new Canine("Rex", 2, -2);}
        );
        assertEquals(
            "age must be a non-negaive integer.", 
            exception.getMessage()
        );
    }

    @Test
    void setHeightThrowsForInvalidNewHeight() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> {dog.setHeight(-2.0);}
        );
        assertEquals(
            "newHeight must be a positive double.",
            exception.getMessage()
        );
    }

    @Test
    void birthdayThrowsForInvalidDeltaAge() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> {dog.birthday(-3);}
        );
        assertEquals(
            "deltaAge must be a positive integer.", 
            exception.getMessage()
        );
    }

    @Test 
    void setHeightMutatesHeight() {
        dog.setHeight(2.3);
        assertEquals(
            2.3,
            dog.getHeight(),
            1e-6  // error tolerance when comparing floating point numbers
        );
    }

    @Test
    void birthdayMutatesAge() {
        int dogAge = dog.age;
        int deltaAge = 1;
        dog.birthday(deltaAge);
        assertEquals(
            dogAge + deltaAge, 
            dog.age
        );
    }

    @Test
    void talkReturnsROOFF() {
        assertEquals(
            "ROOFF",
            dog.talk()
        );
    }

    @Test
    void populationIncrementsWhenInstanceDeclared() {
        int oldPopulation = Canine.population;
        Canine newDog = new Canine();
        assertEquals(
            oldPopulation + 1, 
            Canine.population
        );  
    }
    
}
