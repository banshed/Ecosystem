package ecosystem.Animal;

/**
 * Huntable interface, containing the hunt() method.
 * Used for hunting down an animal.
 */
public interface Huntable extends Moveable {
    /**
     * Makes an animal chase its prey
     * @param prey The animal to hunt
     * @throws AnimalException Animal Not Found or Invalid Target
     */
    void hunt(Animal prey) throws AnimalException;
}