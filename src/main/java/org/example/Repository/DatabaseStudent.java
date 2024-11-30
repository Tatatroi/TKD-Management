package org.example.Repository;

import org.example.Model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseStudent extends DatabaseRepo<org.example.Model.Student> {
    public DatabaseStudent(String dbUrl) {
        super(dbUrl);
    }

    @Override
    public void add(Student student) {
        String sql = "INSERT INTO Students (id, name,lastName,email,address,dateOfBirth,number, beltLevel, session) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, student.getId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getLastName());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getAddress());
            stmt.setInt(6, student.getDateOfBirth());
            stmt.setString(7, student.getNumber());
            stmt.setString(8, student.getBeltLevel());
            stmt.setInt(9, student.getSession());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer RemoveId) {
        super.remove(RemoveId);
    }

    @Override
    public void update(Student obj) {
        super.update(obj);
    }

    @Override
    public Student get(Integer getId) {
        return super.get(getId);
    }

    @Override
    public List<Student> getAll() {
        return super.getAll();
    }
}
