package Repo;

import Model.Contest;
import Model.HasID;
import Model.Person;
import Model.TrainingCamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A repository implementation that stores data in memory.
 * @param <T> The type of objects stored in the repository, which must implement HasId.
 */
public class InMemoryRepo<T extends HasID> implements IRepo<T>{

    Map<Integer, T> data= new HashMap<>();

//    private int firstFreeId = 0;
    /**
     * {@inheritDoc}
     */
    @Override
    public void add(T obj) {
        data.put(obj.getId(), obj);
//        obj.setId(firstFreeId);
//        firstFreeId++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Integer id) {
        data.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(T obj) {
        data.replace(obj.getId(), obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(Integer getId) {
        return data.get(getId);
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll(){
        return data.values().stream().toList();
    }
}
