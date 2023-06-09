package ecosystem.Animal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import ecosystem.Main.Ecosystem;
import ecosystem.Main.GUI;

/**
 * Wolf class, extended from Animal.
 * Different to Rabbit class.
 */
public class Wolf extends Animal 
        implements Huntable{
    /**
     * Radius of hunt: maximum distance of when Wolf runs towards Rabbit
     */
    public static int huntRadius = 150;

    /**
     * Base constructor of Wolf
     */
    public Wolf() {
        this("Wolf", 1, 0, 0);
    }

    /**
     * Wolf constructor, containing its name, age and coordinates
     * @param name Name of Wolf
     * @param age Age of Wolf
     * @param x X coordinate of Wolf
     * @param y Y coordinate of Wolf
     */
    public Wolf(String name, int age, int x, int y) {
        super(name, age, x, y);
        super.stats.color = Color.LIGHT_GRAY;
        super.stats.speed = 2.0;
        super.stats.sound = "Awo";
    }

    /**
     * Wolf's move method
     */
    @Override
    public void move() {
        Random ran = new Random();
        int dirX, dirY;
        do {
            dirX = ran.nextInt(3) - 1;
            dirY = ran.nextInt(3) - 1;
        } while (dirX * dirX + dirY * dirY > 3);

        if (super.getX() + dirX > 0 && super.getX() + dirX < Ecosystem.WIDTH - 32)
            if (super.getY() + dirY > 0 && super.getY() + dirY < Ecosystem.HEIGHT - 32) {
                super.setX(super.getX() + dirX);
                super.setY(super.getY() + dirY);
            }

        super.stats.hunger -= 0.2;
    }

    /**
     * Wolf hunt method to hunt for Rabbit
     * @param prey The animal to hunt
     * @throws AnimalException Animal Not Found or Invalid Target
     */
    @Override
    public void hunt(Animal prey) throws AnimalException{
        if (prey != null) {
            if (prey == this)
                throw new InvalidTargetException(this);

            int dirX = super.getX() - prey.getX(), dirY = super.getY() - prey.getY();
            
            if (x - Math.signum(dirX) * stats.speed > 0 && x - Math.signum(dirX) * stats.speed < Ecosystem.WIDTH - 32)
                if (y - Math.signum(dirY) * stats.speed > 0 && y - Math.signum(dirY) * stats.speed < Ecosystem.HEIGHT - 32)
                {
                    x -= Math.signum(dirX) * stats.speed;
                    y -= Math.signum(dirY) * stats.speed;
                }

            super.stats.hunger -= 2;
            eat(prey);
        } else throw new AnimalNotFoundException();
    }

    /**
     * Wolf's eat method: eats an animal
     * @param prey The prey Animal
     * @throws AnimalException Invalid Target or Animal Not Found
     */
    public void eat(Animal prey) throws AnimalException {
        if (prey != null) {
            if (prey == this)
                throw new InvalidTargetException(this);
            
            if (Math.abs(prey.x - this.x) <= 16 && Math.abs(prey.y - this.y) <= 16)
            {
                Ecosystem.animals.remove(prey);
                stats.hunger += prey.stats.replenishesHunger;

                try {
                    Point p = new Point(prey.x, prey.y);
                    Ecosystem.bloodStains.add(p);
                    Thread.sleep(10000);
                    Ecosystem.bloodStains.remove(p);
                } catch (Exception e) { e.printStackTrace(); }
            }
        } else throw new AnimalNotFoundException();
    }

    /**
     * Draws a Wolf image on wolf's coordinates
     * @param g Graphics object
     */
    public void draw(Graphics g) {
        g.drawImage(GUI.wolfImage, super.getX(), super.getY(), null);
    }

    /**
     * Method used to start Wolf's thread. It checks if the Wolf is alive,
     * checks if it has to starve to death,
     * checks for rabbits in surrounding area,
     * moves.
     */
    public void start() {
        Random random = new Random();
        Thread wolfThread = new Thread(() -> {
            if (stats.alive) {
                starve();
                for (int i = 1; i <= huntRadius; i++) {
                    boolean fleeableFound = false;
                    @SuppressWarnings("unchecked")
                    ArrayList<Animal> tempAnimals = (ArrayList<Animal>) Ecosystem.animals.clone();
                    for (Animal animal : tempAnimals) {
                        if (animal instanceof Fleeable)
                            if (Math.abs(animal.x - x) <= i && Math.abs(animal.y - y) <= i) {
                                try {
                                    hunt(animal);
                                    fleeableFound = true;
                                    break;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                    if (fleeableFound)
                        break;
                }
                move();
            }
            else Ecosystem.animals.remove(this);
        });
        wolfThread.start();
    }
}