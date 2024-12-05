package org.example.Repository;

import org.example.Model.BeltExam;
import org.example.Model.SessionDate;
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
        String removeFromStudentsTrainingCamp = "DELETE FROM StudentsTrainingCamp WHERE idTrainingCamp=?";

        try(PreparedStatement statement = connection.prepareStatement(removeFromStudentsTrainingCamp)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "DELETE FROM TrainingCamps WHERE id=?";

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
            stmt.setDate(1, Date.valueOf(trainingCamp.getStartDate()));
            stmt.setDate(2, Date.valueOf(trainingCamp.getEndDate()));
            stmt.setDouble(3, trainingCamp.getPrice());
            stmt.setString(4, trainingCamp.getCountry());
            stmt.setString(5, trainingCamp.getCity());
            stmt.setString(6, trainingCamp.getAddress());
            stmt.setInt(7, trainingCamp.getNumberOfParticipants());
            stmt.setInt(8, trainingCamp.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String deleteStudentsTrainingCamps = "DELETE From StudentsTrainingCamp WHERE idStud=?";

        try(PreparedStatement statement = connection.prepareStatement(deleteStudentsTrainingCamps)){
            statement.setInt(1,trainingCamp.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String addStudentsTrainingCamps = "INSERT INTO StudentsTrainingCamp (idTrainingCamp,idStud) VALUES (?,?)";

        for(int studentId: trainingCamp.getStudents()) {
            try (PreparedStatement statement = connection.prepareStatement(addStudentsTrainingCamps)) {
                statement.setInt(1, trainingCamp.getId());
                statement.setInt(2, studentId);
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public TrainingCamp get(Integer getId) {
        String sql = "SELECT * FROM TrainingCamps WHERE ID=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,getId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                List<Integer> students = getStudentsFromTrainingCamp(getId);
                return extractFromResultSet(resultSet,students);
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
                List<Integer> students = getStudentsFromTrainingCamp(resultSet.getInt("id"));
                trainingCamps.add(extractFromResultSet(resultSet,students));
            }

            return trainingCamps;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static TrainingCamp extractFromResultSet(ResultSet resultSet,List<Integer> students){
        TrainingCamp trainingCamp = null;
        try {
            trainingCamp = new TrainingCamp(resultSet.getInt("id"),resultSet.getDate("startDate").toString(), resultSet.getDate("endDate").toString(),resultSet.getDouble("price"),
                    resultSet.getString("country"),resultSet.getString("city"),resultSet.getString("address"),resultSet.getInt("numberOfParticipants"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        trainingCamp.setStudents(students);
        return trainingCamp;
    }

    public List<Integer> getStudentsFromTrainingCamp(int trainingCampId) {
        String sql = "SELECT * FROM StudentsTrainingCamp WHERE idTrainingCamp=?";
        List<Integer> students = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,trainingCampId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                students.add(resultSet.getInt("idStud"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
}
