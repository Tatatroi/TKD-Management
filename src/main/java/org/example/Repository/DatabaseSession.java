package org.example.Repository;

import org.example.Exceptions.DatabaseException;
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
    public void add(Session session) throws DatabaseException {
        String sql = "INSERT INTO Sessions (id, difficultyLevel,maximumParticipants,trainerId,pricePerSession) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, session.getId());
            stmt.setString(2, String.valueOf(session.getDifficultyLevel()));
            stmt.setInt(3, session.getMaximumParticipants());
            stmt.setInt(4, session.getTrainer());
            stmt.setFloat(5, (float) session.getPricePerSession());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("DataBase Exception Error");
        }
    }

    @Override
    public void remove(Integer RemoveId) throws DatabaseException {
        String removeFromSession = "DELETE FROM Sessions WHERE ID=?";

        try(PreparedStatement statement = connection.prepareStatement(removeFromSession)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("DataBase Exception Error");
        }
        String removeFromSessionStudents = "DELETE FROM SessionStudents WHERE sessionId=?";

        try(PreparedStatement statement = connection.prepareStatement(removeFromSessionStudents)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("DataBase Exception Error");
        }
    }

    @Override
    public void update(Session session)  throws DatabaseException {
        String sql = "UPDATE Sessions SET difficultyLevel=?, maximumParticipants=?, trainerId=?,pricePerSession=?  WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, session.getDifficultyLevel().name());
            stmt.setInt(2, session.getMaximumParticipants());
            stmt.setInt(3, session.getTrainer());
            stmt.setDouble(4, session.getPricePerSession());
            stmt.setInt(5, session.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("DataBase Exception Error");
        }
    }

    @Override
    public Session get(Integer getId) throws DatabaseException{
        String sql = "SELECT * FROM Sessions WHERE ID=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,getId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                List<Integer> students = getSessionStudents(getId);
                return extractFromResultSet(resultSet,students);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("DataBase Exception Error");
        }
    }

    @Override
    public List<Session> getAll() throws DatabaseException {
        String sql = "SELECT * FROM Sessions";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            List<Session> sessions = new ArrayList<>();

            while(resultSet.next()){
                List<Integer> students = getSessionStudents(resultSet.getInt("id"));
                sessions.add(extractFromResultSet(resultSet,students));
            }

            return sessions;
        } catch (SQLException e) {
            throw new DatabaseException("DataBase Exception Error");
        }
    }
    public static Session extractFromResultSet(ResultSet resultSet,List<Integer> students) throws DatabaseException {
        try {
            Session session = new Session(resultSet.getInt("id"),DifficultyLevel.valueOf(resultSet.getString("difficultyLevel")),resultSet.getInt("maximumParticipants"),
                    resultSet.getInt("trainerId"),resultSet.getDouble("pricePerSession"));
            session.setSessionStudents(students);
            return session;
        } catch (SQLException e) {
            throw new DatabaseException("DataBase Exception Error");
        }
    }

    public List<Integer> getSessionStudents(int sessionId) throws DatabaseException {
        String sql = "SELECT * FROM SessionStudents WHERE sessionId=?";
        List<Integer> studentList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,sessionId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                studentList.add(resultSet.getInt("studentId"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("DataBase Exception Error");
        }
        return studentList;
    }
}
