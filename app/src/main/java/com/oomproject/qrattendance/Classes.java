package com.oomproject.qrattendance;

public class Classes {
    String classId;
    String instructorName;
    String classDate;
    String classQrCode;
    long timeStamp;

    public Classes() {
    }

    public Classes(String classId, String instructorName, String formattedDate, String qrCode, long timeStamp) {
        this.classId = classId;
        this.instructorName = instructorName;
        this.classDate = formattedDate;
        this.classQrCode = qrCode;
        this.timeStamp = timeStamp;
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

    public String getClassQrCode() {
        return classQrCode;
    }

    public void setClassQrCode(String classQrCode) {
        this.classQrCode = classQrCode;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
