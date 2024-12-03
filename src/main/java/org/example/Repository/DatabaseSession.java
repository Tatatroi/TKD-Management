package org.example.Repository;

import org.example.Model.DifficultyLevel;
import org.example.Model.Session;
import org.example.Model.TrainingCamp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSession extends DatabaseRepo<Session> {
    public DatabaseSession(String dbUrl) {
        super(dbUrl);
    }

    @Override
    public void add(Session session) {
        String sql = "INSERT INTO Sessions (id, difficultyLevel,maximumParticipants,trainer,pricePerSession) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, session.getId());
            stmt.setString(2, String.valueOf(session.getDifficultyLevel()));
            stmt.setInt(3, session.getMaximumParticipants());
            stmt.setInt(4, session.getTrainer());
            stmt.setDouble(5, session.getPricePerSession());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer RemoveId) {
        String sql = "DELETE FROM Sessions WHERE ID=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Session session) {
        String sql = "UPDATE Sessions SET difficultyLevel=?, maximumParticipants=?, trainer=?,pricePerSession=?  WHERE ID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, session.getId());
            stmt.setString(2, session.getDifficultyLevel().name());
            stmt.setInt(3, session.getMaximumParticipants());
            stmt.setInt(4, session.getTrainer());
            stmt.setDouble(5, session.getPricePerSession());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Session get(Integer getId) {
        String sql = "SELECT * FROM Sessions WHERE ID=?";

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
    public List<Session> getAll() {
        String sql = "SELECT * FROM Sessions";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            List<Session> sessions = new ArrayList<>();

            while(resultSet.next()){
                sessions.add(extractFromResultSet(resultSet));
            }

            return sessions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Session extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Session(resultSet.getInt("id"),DifficultyLevel.valueOf(resultSet.getString("difficultyLevel")),resultSet.getInt("maximumParticipants"),
                resultSet.getInt("trainer"),resultSet.getDouble("pricePerSession"));
    }
}
