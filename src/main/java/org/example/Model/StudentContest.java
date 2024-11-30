package org.example.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentContest implements HasID{
    private final int studentID;
    private final int contestID;

    public StudentContest(int studentID, int contestID) {
        this.studentID = studentID;
        this.contestID = contestID;
    }

    @Override
    public Integer getId() {
        return this.studentID;
    }

    public int getContestID() {
        return contestID;
    }
}

