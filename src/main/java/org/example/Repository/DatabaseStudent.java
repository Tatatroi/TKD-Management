package org.example.Repository;

import org.example.Model.SessionDate;
import org.example.Model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseStudent extends DatabaseRepo<org.example.Model.Student> {
    public DatabaseStudent(String dbUrl) {
        super(dbUrl);
    }

    @Override
    public void add(Student student) {
        String addToStudent = "INSERT INTO Students (id, name,lastName,email,address,dateOfBirth,number, beltLevel, session) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(addToStudent)) {
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
        String addToSessionStudents = "INSERT INTO SessionStudents (studentId, sessionId) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(addToSessionStudents)) {
            stmt.setInt(1, student.getId());
            stmt.setInt(2, student.getSession());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer RemoveId) {

        String removeChildrenFromParents = "DELETE FROM ParentsStudents WHERE idStudent = ?";
        try(PreparedStatement stmt0 = connection.prepareStatement(removeChildrenFromParents)){
            stmt0.setInt(1, RemoveId);
            stmt0.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }

        String removeFromSessionStudents = "DELETE FROM SessionStudents WHERE studentId=?";

        try(PreparedStatement statement = connection.prepareStatement(removeFromSessionStudents)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String removeFromSessionDates = "DELETE FROM SessionDates WHERE studentId=?";

        try(PreparedStatement statement = connection.prepareStatement(removeFromSessionDates)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String removeFromStudentsTrainingCamp = "DELETE FROM StudentsTrainingCamp WHERE idStud=?";

        try(PreparedStatement statement = connection.prepareStatement(removeFromStudentsTrainingCamp)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String removeFromStudentsContests = "DELETE FROM StudentsContests WHERE idStud=?";

        try(PreparedStatement statement = connection.prepareStatement(removeFromStudentsContests)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String removeFromResultsBeltExams = "DELETE FROM ResultsBeltExams WHERE idStud=?";

        try(PreparedStatement statement = connection.prepareStatement(removeFromResultsBeltExams)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String removeFromStudent = "DELETE FROM Students WHERE id=?";

        try(PreparedStatement statement = connection.prepareStatement(removeFromStudent)){
            statement.setInt(1,RemoveId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Student obj) {
        String updateStudent = "UPDATE Students SET name=?, lastName=?,email=?,address=?,dateOfBirth=?,number=?, beltLevel=?, session=? WHERE id=?";

        try(PreparedStatement statement = connection.prepareStatement(updateStudent)){
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
        String deleteSessionDates = "DELETE From SessionDates WHERE studentId=?";

        try(PreparedStatement statement = connection.prepareStatement(deleteSessionDates)){
            statement.setInt(1,obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String addSessionDates = "INSERT INTO SessionDates (studentId, sessionId,weekday,date,attended) VALUES (?,?,?,?,?)";

        for(SessionDate sd: obj.getSessionDateList()) {
            try (PreparedStatement statement = connection.prepareStatement(addSessionDates)) {
                statement.setInt(1, obj.getId());
                statement.setInt(2, obj.getSession());
                statement.setString(3, sd.getWeekDay());
                statement.setDate(4, Date.valueOf(sd.getDate()));
                statement.setBoolean(5, sd.isAttended());
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Student get(Integer getId) {
        String sql = "SELECT * FROM Students WHERE id=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,getId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                List<SessionDate> sessionDateList = getSessionDateStudent(getId);
                List<Integer> trainingCampIdList = getTrainingCampsStudent(getId);
                List<Integer> contestList = getContestsStudent(getId);
                int parentId = getIdParentfromStudent(resultSet.getInt("id"));
                return extractFromResultSet(resultSet, sessionDateList,trainingCampIdList,contestList,parentId);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> getAll() {
        String sql = "SELECT * FROM Students";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            List<Student> students = new ArrayList<>();

            while(resultSet.next()){
                List<SessionDate> sessionDateList = getSessionDateStudent(resultSet.getInt("id"));
                List<Integer> trainingCampList = getTrainingCampsStudent(resultSet.getInt("id"));
                List<Integer> contestList = getContestsStudent(resultSet.getInt("id"));
                int parentId = getIdParentfromStudent(resultSet.getInt("id"));
                students.add(extractFromResultSet(resultSet,sessionDateList,trainingCampList,contestList,parentId));
            }

            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Student extractFromResultSet(ResultSet resultSet,List<SessionDate> sessionDateList, List<Integer> trainingCamps,List<Integer> contests, int parentId) throws SQLException {
        Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("lastName"),resultSet.getString("email"),
                resultSet.getString("address"),resultSet.getInt("dateOfBirth"),resultSet.getString("number"),resultSet.getString("beltLevel"),
                resultSet.getInt("session"));
        student.setSessionDateList(sessionDateList);
        student.setTrainingCampList(trainingCamps);
        student.setContestList(contests);
        student.setParent(parentId);
        return student;
    }
    public static SessionDate extractFromSessionDate(ResultSet resultSet) throws SQLException {
        return new SessionDate(resultSet.getString("weekday"),String.valueOf(resultSet.getDate("date")),resultSet.getInt("sessionId"),resultSet.getBoolean("attended"));
    }

    public List<SessionDate> getSessionDateStudent(int studentId) throws SQLException {
        String sql = "SELECT * FROM SessionDates WHERE studentId=?";
        List<SessionDate> sessionDateList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,studentId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                sessionDateList.add(extractFromSessionDate(resultSet));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sessionDateList;
    }
    public List<Integer> getTrainingCampsStudent(int studentId) throws SQLException {
        String sql = "SELECT * FROM StudentsTrainingCamp WHERE idStud=?";
        List<Integer> trainingCampList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,studentId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                trainingCampList.add(resultSet.getInt("idTrainingCamp"));
            }
        }
        return trainingCampList;
    }
    public List<Integer> getContestsStudent(int studentId) throws SQLException {
        String sql = "SELECT * FROM StudentsContests WHERE idStud=?";
        List<Integer> contestList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,studentId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                contestList.add(resultSet.getInt("idContest"));
            }
        }
        return contestList;
    }

    public int getIdParentfromStudent(int studentId) throws SQLException {
        String sql = "SELECT * FROM ParentsStudents WHERE idStudent = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,studentId);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("idParent");
            }
            else return 0;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
