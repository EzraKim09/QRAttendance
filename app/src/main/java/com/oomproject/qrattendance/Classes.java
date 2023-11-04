package com.oomproject.qrattendance;

public class Classes {
    String classId;
    String instructorName;
    String classDate;

    public Classes() {
    }

    public Classes(String classId, String instructorName, String formattedDate) {
        this.classId = classId;
        this.instructorName = instructorName;
        this.classDate = formattedDate;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }
}
