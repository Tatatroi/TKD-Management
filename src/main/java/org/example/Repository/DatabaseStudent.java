package org.example.Repository;

import org.example.Model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseStudent extends DatabaseRepo<org.example.Model.Student> {
    public DatabaseStudent(String dbUrl) {
        super(dbUrl);
    }

    @Override
    public void add(Student student) {
        String sql = "INSERT INTO Student (id, name,lastName,email,address,dateOfBirth,number, beltLevel, session) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
    public void remove(Integer RemoveId) throws SQLException {
        String sql = "DELETE FROM Student WHERE ID=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Student obj) {
        String sql = "UPDATE Student SET name=?, lastName=?,email=?,address=?,dateOfBirth=?,number=?, beltLevel=?, session=? WHERE ID=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,obj.getName());
            statement.setString(2,obj.getLastName());
            statement.setString(3,obj.getEmail());
            statement.setString(4,obj.getAddress());
            statement.setInt(5,obj.getDateOfBirth());
            statement.setString(6,obj.getNumber());
            statement.setString(7,obj.getBeltLevel());
            statement.setInt(8,obj.getSession());
            statement.setInt(9,obj.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Student get(Integer getId) throws SQLException {
        String sql = "SELECT * FROM Student WHERE ID=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,getId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> getAll() throws SQLException {
        String sql = "SELECT * FROM Student";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            List<Student> students = new ArrayList<>();

            while(resultSet.next()){
                students.add(extractFromResultSet(resultSet));
            }

            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Student extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("lastName"),resultSet.getString("email"),
                resultSet.getString("address"),resultSet.getInt("dateOfBirth"),resultSet.getString("number"),resultSet.getString("beltLevel"),
                resultSet.getInt("session"));
    }
}
