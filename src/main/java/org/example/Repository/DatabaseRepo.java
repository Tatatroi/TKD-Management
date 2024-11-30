package org.example.Repository;

import org.example.Model.HasID;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class DatabaseRepo<T extends HasID> implements IRepo<T> {
    private final String dbUrl;

    public DatabaseRepo(String dbUrl) {
        this.dbUrl = dbUrl;
    }
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }

    @Override
    public void add(T obj) {

    }

    @Override
    public void remove(Integer RemoveId) {

    }

    @Override
    public void update(T obj) {

    }

    @Override
    public T get(Integer getId) {
        return null;
    }

    @Override
    public List<T> getAll() {
        return List.of();
    }
}
