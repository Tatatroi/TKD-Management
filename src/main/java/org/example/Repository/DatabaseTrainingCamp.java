package org.example.Repository;

import org.example.Model.BeltExam;
import org.example.Model.TrainingCamp;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTrainingCamp extends DatabaseRepo<TrainingCamp> {
    public DatabaseTrainingCamp(String dbUrl) {
        super(dbUrl);
    }

    @Override
    public void add(TrainingCamp trainingCamp) {
        String sql = "INSERT INTO TrainingCamps (id, startDate,endDate,price,country,city,address,numberOfParticipants) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, trainingCamp.getId());
            stmt.setDate(2, Date.valueOf(trainingCamp.getStartDate()));
            stmt.setDate(3, Date.valueOf(trainingCamp.getEndDate()));
            stmt.setDouble(4, trainingCamp.getPrice());
            stmt.setString(5, trainingCamp.getCountry());
            stmt.setString(6, trainingCamp.getCity());
            stmt.setString(7, trainingCamp.getAddress());
            stmt.setInt(8, trainingCamp.getNumberOfParticipants());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer RemoveId) {
        String sql = "DELETE FROM TrainingCamps WHERE ID=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(TrainingCamp trainingCamp) {
        String sql = "UPDATE TrainingCamps SET startDate=?, endDate=?, price=?,country=?,city=?,address=?,numberOfParticipants=?  WHERE ID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, trainingCamp.getId());
            stmt.setDate(2, Date.valueOf(trainingCamp.getStartDate()));
            stmt.setDate(3, Date.valueOf(trainingCamp.getEndDate()));
            stmt.setDouble(4, trainingCamp.getPrice());
            stmt.setString(5, trainingCamp.getCountry());
            stmt.setString(6, trainingCamp.getCity());
            stmt.setString(7, trainingCamp.getAddress());
            stmt.setInt(8, trainingCamp.getNumberOfParticipants());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TrainingCamp get(Integer getId) {
        String sql = "SELECT * FROM TrainingCamps WHERE ID=?";

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
    public List<TrainingCamp> getAll() {
        String sql = "SELECT * FROM TrainingCamps";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            List<TrainingCamp> trainingCamps = new ArrayList<>();

            while(resultSet.next()){
                trainingCamps.add(extractFromResultSet(resultSet));
            }

            return trainingCamps;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static TrainingCamp extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new TrainingCamp(resultSet.getInt("id"),resultSet.getDate("startDate").toString(), resultSet.getDate("endDate").toString(),resultSet.getDouble("price"),
                resultSet.getString("country"),resultSet.getString("city"),resultSet.getString("address"),resultSet.getInt("numberOfParticipants"));
    }
}
