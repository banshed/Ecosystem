package ecosystem.Animal;

/**
 * An exception that occurs when an Animal is not found
 */
public class AnimalNotFoundException extends AnimalException {

    /**
     * Sends an Animal Exception message "Animal not found"
     */
    public AnimalNotFoundException() {
        super("Animal not found.");
    }
}
