package org.example.Repository;

import org.example.Model.BeltExam;
import org.example.Model.Student;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseBeltExam extends DatabaseRepo<BeltExam> {
    public DatabaseBeltExam(String dbUrl) {
        super(dbUrl);
    }

    @Override
    public void add(BeltExam beltExam) {
        String sql = "INSERT INTO BeltExams (id, startDate,endDate,price,country,city,address,beltColor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, beltExam.getId());
            stmt.setDate(2, Date.valueOf(beltExam.getStartDate()));
            stmt.setDate(3, Date.valueOf(beltExam.getEndDate()));
            stmt.setDouble(4, beltExam.getPrice());
            stmt.setString(5, beltExam.getCountry());
            stmt.setString(6, beltExam.getCity());
            stmt.setString(7, beltExam.getAddress());
            stmt.setString(8, beltExam.getBeltColor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer RemoveId) {
        String sql = "DELETE FROM BeltExams WHERE ID=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(BeltExam beltExam) {
        String sql = "UPDATE BeltExams SET startDate=?, endDate=?, price=?,country=?,city=?,address=?,beltColor=?  WHERE ID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, beltExam.getId());
            stmt.setDate(2, Date.valueOf(beltExam.getStartDate()));
            stmt.setDate(3, Date.valueOf(beltExam.getEndDate()));
            stmt.setDouble(4, beltExam.getPrice());
            stmt.setString(5, beltExam.getCountry());
            stmt.setString(6, beltExam.getCity());
            stmt.setString(7, beltExam.getAddress());
            stmt.setString(8, beltExam.getBeltColor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BeltExam get(Integer getId) {
        String sql = "SELECT * FROM BeltExams WHERE ID=?";

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
    public List<BeltExam> getAll() {
        String sql = "SELECT * FROM BeltExams";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            List<BeltExam> beltExams = new ArrayList<>();

            while(resultSet.next()){
                beltExams.add(extractFromResultSet(resultSet));
            }

            return beltExams;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static BeltExam extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new BeltExam(resultSet.getInt("id"),resultSet.getDate("startDate").toString(), resultSet.getDate("endDate").toString(),resultSet.getDouble("price"),
                resultSet.getString("country"),resultSet.getString("city"),resultSet.getString("address"),resultSet.getString("beltColor"));
    }
}
