package org.example.Repository;

import org.example.Model.Parent;
import org.example.Model.Trainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseParent extends DatabaseRepo<org.example.Model.Parent>{
    public DatabaseParent(String dbUrl) {
        super(dbUrl);
    }

    @Override
    public void add(Parent obj) throws SQLException {
        String sql = "INSERT INTO PARENT (name,lastName, email, address, dateOfBirth, number) VALUES (?,?,?,?,?,?)";
        try( PreparedStatement stmt= connection.prepareStatement(sql)) {
            stmt.setString(1, obj.getName());
            stmt.setString(2, obj.getLastName());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getAddress());
            stmt.setInt(5, obj.getDateOfBirth());
            stmt.setString(6, obj.getNumber());
            stmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer RemoveId) throws SQLException {
        String sql = "DELETE FROM PARENT WHERE id = ?";
        try( PreparedStatement stmt= connection.prepareStatement(sql)) {
            stmt.setInt(1, RemoveId);
            stmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Parent obj) throws SQLException {
        String sql = "UPDATE dbo.Parent SET name = ?, lastName = ?, email = ?, address = ?, dateOfBirth = ? WHERE id = ?";
        try( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, obj.getName());
            stmt.setString(2, obj.getLastName());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getAddress());
            stmt.setInt(5, obj.getDateOfBirth());
            stmt.setString(6, obj.getNumber());
            stmt.setInt(7, obj.getId());
            stmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Parent get(Integer getId) throws SQLException {
        String sql =  "SELECT * FROM dbo.Parent WHERE id = ?";
        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, getId);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return new Parent(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getInt("dateOfBirth"),
                            rs.getString("number")
                    );
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Parent> getAll() throws SQLException {
        String sql =  "SELECT * FROM dbo.Parent WHERE id = ?";
        List<Parent> parents = new ArrayList<>();
        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    parents.add(new Parent(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getInt("dateOfBirth"),
                            rs.getString("number")
                    ));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
