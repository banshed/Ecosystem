package ecosystem.Animal;

/**
 * Fleeable interface, containing the flee() method.
 * Used for running away from predators.
 */
public interface Fleeable extends Moveable {
    /**
     * Makes an Animal run away from a Predator
     * @param predator An animal to run away from
     * @throws AnimalNotFoundException Animal not found
     */
    void flee(Animal predator) throws AnimalNotFoundException;
}
