package ecosystem.Animal;

import java.awt.Color;
import java.io.Serializable;

/**
 * A class, which saves all the stats related to Animal:
 * Its name, age, sound, color, hunger, speed and alive status
 */
public class AnimalStats 
        implements Cloneable, Serializable {
    /**
     * Name of Animal
     */
    public String name;
    /**
     * Sound an Animal makes
     */
    public String sound;
    /**
     * Color of Animal
     */
    public Color color;
    /**
     * Animal's hunger points: an animal dies once the hunger reaches 0 or below.
     */
    public double hunger = 1000.0;
    /**
     * Speed of Animal
     */
    public double speed = 1.0;
    /**
     * Alive status of Animal: returns true if Animal is alive, false if it's dead
     */
    public boolean alive = true;
    /**
     * Age of Animal
     */
    public int age;

    /**
     * Amount of Hunger the animal adds when eaten
     */
    public int replenishesHunger = 0;

    /**
     * Clones the Stats of Animal
     * @return a copy of Animal Stats
     * @throws CloneNotSupportedException Cloning is not supported
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
