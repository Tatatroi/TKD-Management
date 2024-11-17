package Model;

/**
 * A functional interface that represents an object with a unique identifier.
 */
@FunctionalInterface
public interface HasID {
    /**
     * Gets the id of the object.
     * @return The id of the object.
     */
    Integer getId();
}
