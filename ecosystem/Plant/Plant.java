package ecosystem.Plant;

import java.awt.Graphics;
import java.io.Serializable;

import ecosystem.Main.GUI;

/**
 * Plant Class
 */
public class Plant implements Serializable {
    /**
     * X coordinate of Plant
     */
    public int x;

    /**
     * Y coordinate of Plant
     */
    public int y;

    /**
     * Hunger Points added when eaten
     */
    public int hungerPoints;

    /**
     * Base constructor for Plant
     * @param x X coordinate of Plant
     * @param y Y coordinate of Plant
     * @param hungerPoints Hunger Points of Plant
     */
    public Plant(int x, int y, int hungerPoints) {
        this.x = x;
        this.y = y;
        this.hungerPoints = hungerPoints;
    }

    /**
     * Draws a Plant image on the screen.
     * @param g Panel's graphics object.
     */
    public void draw(Graphics g) {
        g.drawImage(GUI.carrotImage, this.x, this.y, null);
    }
}
