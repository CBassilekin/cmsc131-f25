# Example of creating a class, following a specification

Your mean boss (YMB) requires a "Canine" class. 
They haven't specified which data types to use for the instance variables.
So, you must decide on acceptable data types on your own. 
(As long as each of your choices is reasonable and sound, YMB will be content.)

## Specifications


### Properties (instance variables):

- `name`: assume it's a public instance variable

- `height`: assume it's declared as a *private* instance variable

- `age`: assume it's a public instance variable


### Class variables:

- `population`: tracks the world's population of canines. 
Assume it starts at 7.0e8


### Constructors:

- **default constructor**: creates a generic dog.
You can invent your own default values.

- **customized constructor**: Receives 3 parameter values, one for each property.


### Behviors (instance methods):

- `getHeight`: returns the value of the current dog's height property.
This is therefore an "accessor" method, since it allows access to the *private* height property.

- `setHeight`: a "mutator" method, allowing mutation (alteration) of a dog's *private* height property.
You should add code so that this method does "data validation", to prohibit someone from changing a height to an invalid value.
In particular, if someone tries to change the height to a non-positive number, a message should be returned stating that the specified value is invalid. But, if someone tries to change a dog's height property to a positive number, then `setHeight` will make that change.

- `talk`: when a Canine talks, the word "ROOFF" should be returned

- `birthday`: receives a parameter value representing a change in age. Then, it increases the dog's `age` property by the specified amount. Also include data validation code for this method.


## Unit testing

Your Canine class must have complete test coverage before your persnickety colleague (YPC) will integrate it. 


# Use case

YMB provides a Java program that will use your Canine class. 