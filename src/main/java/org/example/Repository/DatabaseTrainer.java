package org.example.Repository;

import org.example.Model.Contest;
import org.example.Model.Trainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTrainer extends DatabaseRepo<org.example.Model.Trainer>{
    public DatabaseTrainer(String dbUrl) {
        super(dbUrl);
    }

    @Override
    public void add(Trainer obj) throws SQLException {
        String sql =  "INSERT INTO dbo.Trainer (name,lastName, email, address, dateOfBirth, number, beltLevel) Values (?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, obj.getName());
            stmt.setString(2, obj.getLastName());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getAddress());
            stmt.setInt(5,obj.getDateOfBirth());
            stmt.setString(6, obj.getNumber());
            stmt.setString(7, obj.getBeltLevel());
            stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer RemoveId) throws SQLException {
        String sql =  "DELETE FROM dbo.Trainer WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, RemoveId);
            stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Trainer obj) {
        String sql = "UPDATE dbo.Trainer SET name = ?, lastName = ?, email = ?, address = ?, " +
                "dateOfBirth = ?, number = ?, beltLevel = ? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, obj.getName());
            stmt.setString(2, obj.getLastName());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getAddress());
            stmt.setInt(5, obj.getDateOfBirth());
            stmt.setString(6, obj.getNumber());
            stmt.setString(7, obj.getBeltLevel());
            stmt.setInt(8, obj.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Trainer get(Integer getId) throws SQLException {
        String sql =  "SELECT * FROM dbo.Trainer WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, getId);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return new Trainer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getInt("dateOfBirth"),
                            rs.getString("number"),
                            rs.getString("beltLevel")
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
    public List<Trainer> getAll() throws SQLException {
        String sql =  "SELECT * FROM dbo.Trainer";
        List<Trainer> trainers = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    trainers.add(new Trainer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getInt("dateOfBirth"),
                            rs.getString("number"),
                            rs.getString("beltLevel")
                    ));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return trainers;
    }


}