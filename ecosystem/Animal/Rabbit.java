package ecosystem.Animal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import ecosystem.Plant.Plant;
import ecosystem.Main.Ecosystem;
import ecosystem.Main.GUI;

/**
 * Rabbit class, extended from Animal.
 * Different to Wolf class.
 */
public class Rabbit extends Animal 
        implements Fleeable{

    /**
     * Radius of flee: maximum distance of when Rabbit starts running away from Wolf
     */
    public static int fleeRadius = 200;

    /**
     * Radius of plants: maximum distance of when Rabbit starts running towards Plant
     */
    public static int plantRadius = 200;

    /**
     * Base constructor of Rabbit
     */
    public Rabbit() {
        this("Rabbit", 1, 0, 0);
    }

    /**
     * Rabbit constructor, containing its name, age and coordinates
     * @param name Name of Rabbit
     * @param age Age of Rabbit
     * @param x X coordinate of Rabbit
     * @param y Y coordinate of Rabbit
     */
    public Rabbit(String name, int age, int x, int y) {
        super(name, age, x, y);
        super.stats.color = Color.ORANGE;
        super.stats.sound = "Shush";
        super.stats.replenishesHunger = 250;
    }

    /**
     * Rabbit's move method
     */
    @Override
    public void move() { //can only move in 4 directions
        int[][] moves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Random ran = new Random();
        int r = ran.nextInt(4);

        if (super.getX() + moves[r][0] > 0 && super.getX() + moves[r][0] < Ecosystem.WIDTH - 32)
            if (super.getY() + moves[r][1] > 0 && super.getY() + moves[r][1] < Ecosystem.HEIGHT - 32) {
                x += moves[r][0];
                y += moves[r][1];
        }
        super.stats.hunger -= 0.05;
    }

    /**
     * Rabbit's flee method to run away from Wolf
     * @param predator An animal to run away from
     * @throws AnimalNotFoundException Animal Not Found
     */
    @Override
    public void flee(Animal predator) throws AnimalNotFoundException{
        if (predator != null) {
            int dirX = super.getX() - predator.getX(), dirY = super.getY() - predator.getY();

            if (x + Math.signum(dirX) * stats.speed > 0 && x + Math.signum(dirX) * stats.speed < Ecosystem.WIDTH - 32)
                if (y + Math.signum(dirY) * stats.speed > 0 && y + Math.signum(dirY) * stats.speed < Ecosystem.HEIGHT - 32)
                {
                    x += Math.signum(dirX) * stats.speed;
                    y += Math.signum(dirY) * stats.speed;
                }

            super.stats.hunger -= 0.05;
        }
        else
            throw new AnimalNotFoundException();
    }

    /**
     * Rabbit's hunt method: used by Rabbit to hunt for plants
     * @param plant The plant Rabbit is trying to hunt for
     */
    public void hunt(Plant plant) {
        if (plant != null) {

            int dirX = x - plant.x, dirY = y - plant.y;

            if (x - Math.signum(dirX) * stats.speed > 0 && x - Math.signum(dirX) * stats.speed < Ecosystem.WIDTH - 32)
                if (y - Math.signum(dirY) * stats.speed > 0 && y - Math.signum(dirY) * stats.speed < Ecosystem.HEIGHT - 32)
                {
                    x -= Math.signum(dirX) * stats.speed;
                    y -= Math.signum(dirY) * stats.speed;
                }

            super.stats.hunger -= 0.05;
            eat(plant);
        }
    }

    /**
     * Rabbit's eat method: eats the target plant
     * @param plant The plant Rabbit is trying to eat
     */
    public void eat(Plant plant) {
        if (Math.abs(plant.x - this.x) <= 16 && Math.abs(plant.y - this.y) <= 16)
        {
            super.stats.hunger += plant.hungerPoints;
            Ecosystem.plants.remove(plant);
        }
    }

    /**
     * Draws a Rabbit image on Rabbit's coordinates
     * @param g Graphics object
     */
    public void draw(Graphics g) {
        g.drawImage(GUI.rabbitImage, super.getX(), super.getY(), null);
    }

    /**
     * Method used to start a Rabbit's thread. It checks if the Rabbit is alive,
     * checks if it has to starve to death,
     * looks for wolves in surrounding area,
     * hunts for plants,
     * moves.
     */
    public void start() {
        Animal thisAnimal = this;
        Random random = new Random();
        Thread rabbitThread = new Thread(new Runnable() {
            public void run() {
                if (stats.alive) {
                    starve();
                    findWolf();
                    for (int i = 1; i <= plantRadius; i++) {
                        boolean plantFound = false;
                        @SuppressWarnings("unchecked")
                        ArrayList<Plant> tempPlants = (ArrayList<Plant>) Ecosystem.plants.clone();
                        for (Plant plant : tempPlants) {
                            if (Math.abs(plant.x - x) <= i && Math.abs(plant.y - y) <= i) {
                                findWolf();
                                hunt(plant);
                                plantFound = true;
                                break;
                            }
                        }
                        if (plantFound)
                            break;
                    }
                    move();
                }
                else Ecosystem.animals.remove(thisAnimal);
            }
            
            public void findWolf() {
                for (int i = 1; i <= fleeRadius; i++) {
                    boolean huntableFound = false;

                    @SuppressWarnings("unchecked")
                    ArrayList<Animal> tempAnimals = (ArrayList<Animal>) Ecosystem.animals.clone();
                    
                    for (Animal animal : tempAnimals) {
                        if (animal instanceof Wolf)
                            if (Math.abs(animal.x - x) <= i && Math.abs(animal.y - y) <= i) {
                                try {
                                    flee(animal);
                                    huntableFound = true;
                                    break;
                                } catch (Exception e) { e.printStackTrace(); }
                            }
                    }
                    if (huntableFound)
                        break;
                }
            }
        });
        rabbitThread.start();
    }
}