package ecosystem.Animal;

/**
 * An exception that occurs when a target animal is invalid/bad
 */
public class InvalidTargetException extends AnimalException {

    /**
     * Invalid target animal
     */
    public Animal target;
    /**
     * X coordinate of invalid animal
     */
    public int x;
    /**
     * Y coordinate of invalid animal
     */
    public int y;

    /**
     * Sends a message containing the invalid animal.
     * Saves the invalid animal and its coordinates;
     * @param target Invalid animal
     */
    public InvalidTargetException(Animal target) {
        super("Animal is targeting bad animal: " + target.toString() + " at X=" + target.getX() + ", Y=" + target.getY());
        this.target = target;
        this.x = target.getX();
        this.y = target.getY();
    }
}
