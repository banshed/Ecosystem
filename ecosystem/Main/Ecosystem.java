package ecosystem.Main;

import ecosystem.Animal.*;
import ecosystem.Plant.Plant;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

/**
 * Ecosystem panel/field, containing all animals, plants and simulations
 */
public class Ecosystem extends JPanel
        implements Runnable {
    /**
     * Max Width of panel
     */
    public final static int WIDTH = 800;
    /**
     * Max Height of panel
     */
    public final static int HEIGHT = 600;
    /**
     * List of Animals in panel
     */
    public static ArrayList<Animal> animals = new ArrayList<>();
    /**
     * List of Plants in panel
     */
    public static ArrayList<Plant> plants = new ArrayList<>();
    /**
     * List of Coordinates of Blood Stains in panel
     */
    public static ArrayList<Point> bloodStains = new ArrayList<>();
    /**
     * Used to check if simulation is currently going
     */
    public boolean isSimulating = false;
    /**
     * Panel's background image
     */
    public Image img;

    /**
     * Paints all animals, plants and blood stains onto the field.
     * @param g Panel's graphics object.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);

        @SuppressWarnings("unchecked")
        ArrayList<Animal> tempAnimals = (ArrayList<Animal>) animals.clone();
        for (Animal animal: tempAnimals) {
            if (animal instanceof Wolf)
                ((Wolf)animal).draw(g);
            else
                ((Rabbit)animal).draw(g);
        }
        
        @SuppressWarnings(value = "unchecked")
        ArrayList<Plant> tempPlants = (ArrayList<Plant>) plants.clone();
        for (Plant plant: tempPlants)
            plant.draw(g);
        
        @SuppressWarnings(value = "unchecked")
        ArrayList<Point> tempBloodStains = (ArrayList<Point>) bloodStains.clone();
        for (Point p : tempBloodStains)
            g.drawImage(ecosystem.Main.GUI.bloodImage, (int)p.getX(), (int)p.getY(), null);
    }

    /**
     * Adds an animal to a List of Animals and calls a repaint function.
     * @param animal Animal to add to list.
     */
    public void addAnimal(Animal animal) {
        animals.add(animal);
        repaint();
    }

    /**
     * Removes an animal from a List of Animals and calls a repaint function.
     * @param animal Animal to remove from list.
     */
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
        repaint();
        animal = null;
    }

    /**
     * Adds a plant to a List of Plants and calls a repaint function.
     * @param plant Plant to add to List.
     */
    public void addPlant(Plant plant) {
        plants.add(plant);
        repaint();
    }

    /**
     * This method is used to start the Ecosystem.
     * For every animal in the List, it starts a new Thread.
     * Calls a function to repaint the field.
     */
    public void run() {
        try {
            while (true) {
                @SuppressWarnings("unchecked")
                ArrayList<Animal> tempAnimal = (ArrayList<Animal>) animals.clone();

                for (Animal animal : tempAnimal)
                    if (animal.stats.alive)
                        if (animal instanceof Wolf)
                            ((Wolf)animal).start();
                        else
                            ((Rabbit)animal).start();

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        } catch (Exception e) { e.printStackTrace();}
    }

    /**
     * Starts the Ecosystem simulation:
     * a new thread is created where every second there is a chance
     * of a new animal or plant spawning into the field.
     * While simulation is going, User may proceed to change specific Animal stats,
     * add new animals into the field, or clear the screen.
     */
    public void startSimulation() {
        isSimulating = true;

        Thread simulationThread = new Thread(() -> {
            while (isSimulating) {
                Random ran = new Random();
                int chance = ran.nextInt(100);
                if (chance <= 10)
                    addAnimal(new Wolf("Wolf", 1, ran.nextInt(WIDTH - 32), ran.nextInt(HEIGHT - 32)));
                else if (chance <= 35)
                    addAnimal(new Rabbit("Rabbit", 1, ran.nextInt(WIDTH - 32), ran.nextInt(HEIGHT - 32)));
                else if (chance <= 50)
                    addPlant(new Plant(ran.nextInt(WIDTH - 32), ran.nextInt(HEIGHT - 32), 150));

                try {
                    Thread.sleep(1000);
                } catch (Exception exc) { exc.printStackTrace(); }
            }
        });
        simulationThread.start();
    }
}
