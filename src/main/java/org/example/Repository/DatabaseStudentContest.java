//package org.example.Repository;
//
//import org.example.Model.StudentContest;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.List;
//
//public class DatabaseStudentContest extends DatabaseRepo<StudentContest>{
//    public DatabaseStudentContest(String dbUrl) {
//        super(dbUrl);
//    }
//
//    @Override
//    public void add(StudentContest studentContest) {
//        String sql = "INSERT INTO StudentsContests (idStud, idContest) VALUES (?, ?)";
//        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, studentContest.getId());
//            stmt.setInt(2, studentContest.getContestID());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void remove(Integer RemoveId) throws SQLException {
//        super.remove(RemoveId);
//    }
//
//    @Override
//    public void update(StudentContest obj) {
//        super.update(obj);
//    }
//
//    @Override
//    public StudentContest get(Integer getId) throws SQLException {
//        return super.get(getId);
//    }
//
//    @Override
//    public List<StudentContest> getAll() throws SQLException {
//        return super.getAll();
//    }
//}
