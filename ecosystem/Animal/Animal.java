package ecosystem.Animal;

import java.awt.*;
import java.io.Serializable;
import ecosystem.Main.Ecosystem;

/**
 * Main Animal class
 */
public class Animal
        implements Cloneable, Serializable{
    /**
     * Coordinate X of Animal
     */
    public int x;
    /**
     * Coordinate Y of Animal
     */
    public int y;
    /**
     * Animal's stats, such as its name, age, colour, sound, etc.
     */
    public AnimalStats stats = new AnimalStats();

    /**
     * Animal Constructor with no parameters
     */
    public Animal() {
        this("Unknown", 1, 0, 0);
    }

    /**
     * Animal constructor with its coordinates
     * @param x X coordinate of Animal
     * @param y Y coordinate of Animal
     */
    public Animal(int x, int y) {
        this("Unknown", 1, x, y);
    }

    /**
     * Animal constructor with its name, age and coordinates
     * @param name Name of Animal
     * @param age Age of Animal
     * @param x X coordinate of Animal
     * @param y Y coordinate of Animal
     */
    public Animal(String name, int age, int x, int y) {
        this.stats.name = name;
        this.stats.age = age;
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if Animal starves to death. If Animal's hunger is below or equal to 0,
     * the animal dies. Its coordinates are added to the bloodStains List for 10 seconds,
     * to draw a blood stain image on a field.
     */
    public void starve() {
        if (this.stats.hunger <= 0)
        {
            Ecosystem.animals.remove(this);
            this.stats.alive = false;
            
            try {
                Point p = new Point(x, y);
                Ecosystem.bloodStains.add(p);
                Thread.sleep(10000);
                Ecosystem.bloodStains.remove(p);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    /**
     * Spawns a new child of an Animal in the same location
     */
    public void mate() {
        try {
            Animal clone = (Animal)this.clone();
            clone.x = this.x;
            clone.y = this.y;
            Ecosystem.animals.add(clone);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Animal clone = (Animal)super.clone();
        clone.stats = (AnimalStats)stats.clone();
        return clone;
    }

    /**
     * Sets the X coordinate of Animal
     * @param x new X coordinate of Animal
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the X coordinate of Animal
     * @return Y coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Sets the Y coordinate of Animal
     * @param y new Y coordinate of Animal
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns Y coordinate of Animal
     * @return Y coordinate
     */
    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "Name: " + this.stats.name + ", age: " + this.stats.age + " x:" + this.x + " y:" + this.y;
    }
}
