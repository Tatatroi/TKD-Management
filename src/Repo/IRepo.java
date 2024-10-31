package Repo;

import Model.Person;

import java.util.List;

public interface IRepo <T extends Person>{
    void add(T obj);
    void remove(Integer RemoveId);
    void update(T obj);
    T get(Integer getId);
    List<T> getAll();
}
