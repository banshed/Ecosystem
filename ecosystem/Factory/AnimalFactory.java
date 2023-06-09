package ecosystem.Factory;

import ecosystem.Animal.Animal;
import ecosystem.Animal.Rabbit;
import ecosystem.Animal.Wolf;

/**
 * AnimalFactory class used to create Animals based on arguments
 */
public class AnimalFactory {

    /**
     * Method used to create an animal based on it's type
     * @param type The type of Animal (Rabbit or Wolf)
     * @param name Name of Animal
     * @param age Age of Animal
     * @param x X coordinate of Animal
     * @param y Y coordinate of Animal
     * @return An animal based on the type with specified stats/null for invalid type.
     */
    public static Animal getAnimal(String type, String name, int age, int x, int y) {
        if ("Wolf".equalsIgnoreCase(type)) return new Wolf(name, age, x, y);
        else if ("Rabbit".equalsIgnoreCase(type)) return new Rabbit(name, age, x, y);

        return null;
    }
}
