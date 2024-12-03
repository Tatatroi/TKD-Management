package org.example.Repository;

import org.example.Model.HasID;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class DatabaseRepo<T extends HasID> implements IRepo<T>,AutoCloseable {
    protected final Connection connection;

    public DatabaseRepo(String dbUrl) {
        try {
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
