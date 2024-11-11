package Model;

import java.util.ArrayList;
import java.util.List;

public class Parent extends Person{
    List<Student> children = new ArrayList<>();
    public Parent(Integer id,String name, String lastName, String email, String address, int dateOfBirth) {
        super(id,name, lastName, email, address, dateOfBirth);
    }

    public List<Student> getChildren() {
        return children;
    }

    public void setChildren(List<Student> children) {
        this.children = children;
    }
}
