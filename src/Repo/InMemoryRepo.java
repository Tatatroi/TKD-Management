package Repo;

import Model.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepo<T extends Person> implements IRepo<T>{

    Map<Integer, T> data;
    private int firstFreeId = 0;

    @Override
    public void add(T obj) {
        data.put(firstFreeId, obj);
        obj.setId(firstFreeId);
        firstFreeId++;
    }

    @Override
    public void remove(Integer id) {
        data.remove(id);
    }

    @Override
    public void update(T obj) {
        data.replace(obj.getId(), obj);
    }

    @Override
    public T get(Integer getId) {
        return data.get(getId);
    }

    public List<T> getAll(){
        return data.values().stream().toList();
    }
}
