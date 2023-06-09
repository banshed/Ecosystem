package ecosystem.Animal;

/**
 * An exception that occurs when something is wrong with Animal
 */
public class AnimalException extends Exception{
    /**
     * Base of an Animal Exception
     * @param message Exception message
     */
    public AnimalException(String message) {
        super(message);
    }
}
