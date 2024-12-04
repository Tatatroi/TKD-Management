package org.example.Repository;

import org.example.Model.Contest;
import org.example.Model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseContest extends DatabaseRepo<org.example.Model.Contest>{
    public DatabaseContest(String dbUrl) {
        super(dbUrl);
    }

    @Override
    public void add(Contest obj) throws SQLException {
        String sql = "INSERT INTO dbo.Contest(id,startDate, endDate, price, country, city, name, address) values(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, obj.getId());
            stmt.setString(2, obj.getStartDate());
            stmt.setString(3, obj.getEndDate());
            stmt.setDouble(4, obj.getPrice());
            stmt.setString(5, obj.getCountry());
            stmt.setString(6, obj.getCity());
            stmt.setString(7, obj.getName());
            stmt.setString(8, obj.getAddress());
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer RemoveId) throws SQLException {

        String removeStudentContests = "DELETE FROM StudentsContests WHERE idContest = ?";

        try(PreparedStatement stmt = connection.prepareStatement(removeStudentContests)){
            stmt.setInt(1, RemoveId);
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "DELETE FROM Contest WHERE Contest.id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, RemoveId);
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Contest obj){
        String sql = "UPDATE dbo.Contest SET startDate = ?, endDate = ?, price = ?, country = ?, city = ?, name = ?, address = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Setăm toți parametrii, indiferent dacă s-au schimbat sau nu
            stmt.setString(1, obj.getStartDate());
            stmt.setString(2, obj.getEndDate());
            stmt.setDouble(3, obj.getPrice());
            stmt.setString(4, obj.getCountry());
            stmt.setString(5, obj.getCity());
            stmt.setString(6, obj.getName());
            stmt.setString(7, obj.getAddress());
            stmt.setInt(8, obj.getId());

            // Executăm query-ul
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String deleteStudentsFromContest = "DELETE From StudentsContests WHERE idContest = ?";
        try(PreparedStatement stmt2 = connection.prepareStatement(deleteStudentsFromContest)){
            stmt2.setInt(1, obj.getId());
            stmt2.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }

        String insertStudentsContests = "INSERT INTO StudentsContests(idContest, idStud) VALUES(?,?)";

        for(int idStudent: obj.getStudents()){
            try(PreparedStatement stmt = connection.prepareStatement(insertStudentsContests)){
                stmt.setInt(1, obj.getId());
                stmt.setInt(2, idStudent);
                stmt.executeUpdate();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Contest get(Integer getId) throws SQLException {
        String sql = "SELECT * FROM Contest WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, getId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Creăm un obiect Contest din valorile obținute
                    List<Integer> studentList = getContestsStudents(getId);
                    return extractContest(rs, studentList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Dacă nu există un concurs cu ID-ul dat
    }


    @Override
    public List<Contest> getAll() throws SQLException {
        String sql = "SELECT * FROM Contest";
        List<Contest> contests = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    List<Integer> studentList = getContestsStudents(rs.getInt("idContest"));
                    contests.add(extractContest(rs,studentList));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contests;
    }

    public List<Integer> getContestsStudents(int ContestId) throws SQLException {
        String sql = "SELECT * FROM StudentsContests WHERE idContest = ?";
        List<Integer> students = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ContestId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    students.add(rs.getInt("idStud"));
                }
            }
        }
        return students;
    }

    public static Contest extractContest(ResultSet rs, List<Integer> students) throws SQLException {
        Contest contest = new Contest(rs.getInt("id"),
                rs.getString("startDate"),
                rs.getString("endDate"),
                rs.getDouble("price"),
                rs.getString("country"),
                rs.getString("city"),
                rs.getString("name"),
                rs.getString("address"));
        contest.setStudents(students);
        return contest;
    }

}
