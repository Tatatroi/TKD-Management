package Model;

import java.util.List;

public class Parent extends Person{
    List<Student> children;
    public Parent(Integer id,String name, String lastName, String email, String address, int dateOfBirth,List<Student> children) {
        super(id,name, lastName, email, address, dateOfBirth);
        this.children=children;
    }

    public List<Student> getChildren() {
        return children;
    }

    public void setChildren(List<Student> children) {
        this.children = children;
    }
}
