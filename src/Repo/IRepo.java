package Repo;

import Model.HasID;
import Model.Person;

import java.util.List;

/**
 * An interface that defines the basic CRUD operations for a repository.
 * @param <T> The type of objects stored in the repository, which must implement HasId.
 */
public interface IRepo <T extends HasID>{
    /**
     * Adds a new object in the repository.
     * @param obj The object to add.
     */
    void add(T obj);

    /**
     * Deletes an object from the repository by its ID.
     * @param RemoveId The unique identifier of the object to delete.
     */
    void remove(Integer RemoveId);

    /**
     * Updates an existing object in the repository.
     * @param obj The object to update.
     */
    void update(T obj);

    /**
     * Retrieves an object from the repository by its ID.
     * @param getId The unique identifier of the object to retrieve.
     * @return The object with the specified ID, or null if not found.
     */
    T get(Integer getId);

    /**
     * Retrieves all objects from the repository.
     * @return A list of all objects in the repository.
     */
    List<T> getAll();
}
