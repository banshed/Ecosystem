package ecosystem.Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.nio.file.Paths;
import ecosystem.Animal.*;
import ecosystem.Plant.*;

/**
 * GUI of the Program
 */
public class GUI{

    /**
     * Wolf Icon image
     */
    public static BufferedImage wolfImage;

    /**
     * Rabbit Icon image
     */
    public static BufferedImage rabbitImage;

    /**
     * Carrot Icon image
     */
    public static BufferedImage carrotImage;

    /**
     * Blood Stain Icon image
     */
    public static BufferedImage bloodImage;

    /**
     * Main method of GUI: adds all buttons, panels, creates the Ecosystem, etc
     * @param args None in here
     */
    public static void main(String[] args){

        Ecosystem ecosystem = new Ecosystem();
        Random ran = new Random();

        JFrame frame = new JFrame("Ecosystem");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton saveButton = new JButton("Save"), loadButton = new JButton("Load"),
            addWolf = new JButton("Add Wolf"), addRabbit = new JButton("Add Rabbit"),
            addCarrot = new JButton("Add Carrot"), clearButton = new JButton("Clear Screen"),
            simulationButton = new JButton("Start Simulation");
        
        JTextField wolfRadiusTF = new JTextField(Integer.toString(Wolf.huntRadius), 1),
                rabbitFleeRadiusTF = new JTextField(Integer.toString(Rabbit.fleeRadius), 1),
                rabbitCarrotHuntRadiusTF = new JTextField(Integer.toString(Rabbit.plantRadius), 1),
                wolfSpeedTF = new JTextField("2", 1),
                rabbitSpeedTF = new JTextField("1", 1);

        try {
            String path = Paths.get("").toAbsolutePath().toString();
            rabbitImage = ImageIO.read(new File(path + "\\pictures\\rabbit32_32.png"));
            carrotImage = ImageIO.read(new File(path + "\\pictures\\carrot32_32.png"));
            wolfImage = ImageIO.read(new File(path + "\\pictures\\wolf32_32.png"));
            ecosystem.img = ImageIO.read(new File(path + "\\pictures\\bg.png"));
            bloodImage = ImageIO.read(new File(path + "\\pictures\\blood32_32.png"));
        } catch (Exception e) { e.printStackTrace(); }

        saveButton.addActionListener(e -> {
            try {
                FileOutputStream fos = new FileOutputStream("save.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(Ecosystem.animals);
                oos.writeObject(Ecosystem.plants);

                oos.close();
            } catch (Exception exc) { exc.printStackTrace(); }
        });

        loadButton.addActionListener(e -> {
            try {
                FileInputStream fis = new FileInputStream("save.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);

                Ecosystem.animals = (ArrayList<Animal>) ois.readObject();
                Ecosystem.plants = (ArrayList<Plant>) ois.readObject();
                
                ecosystem.repaint();
                ois.close();
            } catch (Exception exc) { exc.printStackTrace(); }
        });

        addWolf.addActionListener(e -> ecosystem.addAnimal(new Wolf("Wolf", 1, ran.nextInt(Ecosystem.WIDTH - 32), ran.nextInt(Ecosystem.HEIGHT - 32))));

        addRabbit.addActionListener(e -> ecosystem.addAnimal(new Rabbit("Rabbit", 1, ran.nextInt(Ecosystem.WIDTH - 32), ran.nextInt(Ecosystem.HEIGHT - 32))));

        addCarrot.addActionListener(e -> ecosystem.addPlant(new Plant(ran.nextInt(Ecosystem.WIDTH - 32), ran.nextInt(Ecosystem.HEIGHT - 32), 150)));

        clearButton.addActionListener(e -> {
            Ecosystem.animals.clear();
            Ecosystem.plants.clear();
            Ecosystem.bloodStains.clear();
        });

        simulationButton.addActionListener(e -> {
            if (!ecosystem.isSimulating) {
                simulationButton.setText("Stop Simulation");
                ecosystem.isSimulating = true;
                ecosystem.startSimulation();
            }
            else {
                simulationButton.setText("Start Simulation");
                ecosystem.isSimulating = false;
            }
        });

        wolfRadiusTF.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = wolfRadiusTF.getText();
                if (ke.getKeyChar() == '\n' || ke.getKeyChar() == '\r') {
                    try {
                        Wolf.huntRadius = Integer.parseInt(value);
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
        });

        rabbitFleeRadiusTF.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = rabbitFleeRadiusTF.getText();
                if (ke.getKeyChar() == '\n' || ke.getKeyChar() == '\r') {
                    try {
                        Rabbit.fleeRadius = Integer.parseInt(value);
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
        });

        rabbitCarrotHuntRadiusTF.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = rabbitCarrotHuntRadiusTF.getText();
                if (ke.getKeyChar() == '\n' || ke.getKeyChar() == '\r') {
                    try {
                        Rabbit.plantRadius = Integer.parseInt(value);
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
        });

        wolfSpeedTF.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = wolfSpeedTF.getText();
                if (ke.getKeyChar() == '\n' || ke.getKeyChar() == '\r') {
                    int n;
                    try {
                        n = Integer.parseInt(value);
                    } catch (Exception e) { e.printStackTrace(); n = 2; }

                    for(Animal animal : Ecosystem.animals) 
                        if (animal instanceof Wolf)
                            animal.stats.speed = n;

                    wolfSpeedTF.setText(Integer.toString(n));
                }
            }
        });

        rabbitSpeedTF.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = rabbitSpeedTF.getText();
                if (ke.getKeyChar() == '\n' || ke.getKeyChar() == '\r') {
                    int n;
                    try {
                        n = Integer.parseInt(value);
                    } catch (Exception e) { e.printStackTrace(); n = 1; }

                    for (Animal animal : Ecosystem.animals)
                        if (animal instanceof Rabbit)
                            animal.stats.speed = n;
                    
                    rabbitSpeedTF.setText(Integer.toString(n));
                }
            }
        });

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(panel, BorderLayout.WEST);
        contentPane.add(ecosystem, BorderLayout.CENTER);
        contentPane.add(Box.createVerticalStrut(10), BorderLayout.NORTH);

        Dimension buttonDimension = new Dimension(130, 25);
        saveButton.setMaximumSize(buttonDimension);
        loadButton.setMaximumSize(buttonDimension);
        addWolf.setMaximumSize(buttonDimension);
        addRabbit.setMaximumSize(buttonDimension);
        addCarrot.setMaximumSize(buttonDimension);
        clearButton.setMaximumSize(buttonDimension);
        simulationButton.setMaximumSize(buttonDimension);

        Dimension tfDimension = new Dimension(10, 25);
        wolfRadiusTF.setMinimumSize(tfDimension);
        rabbitFleeRadiusTF.setMinimumSize(tfDimension);
        rabbitCarrotHuntRadiusTF.setMinimumSize(tfDimension);
        wolfSpeedTF.setMinimumSize(tfDimension);
        rabbitSpeedTF.setMinimumSize(tfDimension);

        panel.setBorder(new EmptyBorder(0, 10, 10, 10));
        ecosystem.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.setPreferredSize(new Dimension(150, 600));
        ecosystem.setPreferredSize(new Dimension(810, 610));

        panel.add(saveButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(loadButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addWolf);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addRabbit);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addCarrot);
        panel.add(Box.createVerticalStrut(10));
        panel.add(clearButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(simulationButton);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Wolf Hunt Radius:"));
        panel.add(wolfRadiusTF);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Rabbit Flee Radius:"));
        panel.add(rabbitFleeRadiusTF);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Rabbit Carrot Hunt Radius:"));
        panel.add(rabbitCarrotHuntRadiusTF);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Wolf Speed:"));
        panel.add(wolfSpeedTF);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Rabbit Speed:"));
        panel.add(rabbitSpeedTF);
        panel.add(Box.createVerticalStrut(10));

        panel.add(Box.createVerticalStrut(3000));

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        ecosystem.run();
    }
}
