package org.example.Repository;
import org.example.Model.Parent;
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
        String sql = "INSERT INTO PARENT (id,name,lastName, email, address, dateOfBirth, number) VALUES (?,?,?,?,?,?,?)";
        try( PreparedStatement stmt= connection.prepareStatement(sql)) {
            stmt.setInt(1, obj.getId());
            stmt.setString(2, obj.getName());
            stmt.setString(3, obj.getLastName());
            stmt.setString(4, obj.getEmail());
            stmt.setString(5, obj.getAddress());
            stmt.setInt(6, obj.getDateOfBirth());
            stmt.setString(7, obj.getNumber());
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

        String deleteChildrenFromParent = "DELETE From ParentsStudents WHERE idParent = ?";
        try(PreparedStatement stmt2 = connection.prepareStatement(deleteChildrenFromParent)){
            stmt2.setInt(1, obj.getId());
            stmt2.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }


        String insertChildrenParents = "INSERT INTO ParentsStudents(idParent, idStudent) VALUES(?,?)";
        for(int idStudent: obj.getChildren()){
            try(PreparedStatement stmt = connection.prepareStatement(insertChildrenParents)){
                stmt.setInt(1, obj.getId());
                stmt.setInt(2, idStudent);
                stmt.executeUpdate();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public Parent get(Integer getId) throws SQLException {
        String sql =  "SELECT * FROM dbo.Parent WHERE id = ?";
        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, getId);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    List<Integer> children = getParentChildren(getId);
                    return extractContest(rs,children);
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
                    List<Integer> children = getParentChildren(rs.getInt("id"));
                    parents.add(extractContest(rs,children));
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

    public List<Integer> getParentChildren(int ParentId) throws SQLException {
        String sql = "SELECT * FROM ParentsStudents WHERE idParent = ?";
        List<Integer> children = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ParentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    children.add(rs.getInt("idParent"));
                }
            }
        }
        return children;
    }

    public static Parent extractContest(ResultSet rs, List<Integer> students) throws SQLException {
        Parent parent = new Parent(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getInt("dateOfBirth"),
                rs.getString("number"));
        parent.setChildren(students);
        return parent;
    }

}
