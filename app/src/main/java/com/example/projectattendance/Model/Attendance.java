package com.example.projectattendance.Model;

public class Attendance {
    String id;
    String year, semester, date, subject;
    String RollNO;

    public Attendance() {

    }

    public Attendance(String year, String semester, String date, String subject, String rollNO) {
        this.year = year;
        this.semester = semester;
        this.date = date;
        this.subject = subject;
        RollNO = rollNO;
    }

//year, semester, date, subject, studentuid


    public Attendance(String id, String year, String semester, String date, String subject, String rollNO) {
        this.id = id;
        this.year = year;
        this.semester = semester;
        this.date = date;
        this.subject = subject;
        RollNO = rollNO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRollNO() {
        return RollNO;
    }

    public void setRollNO(String rollNO) {
        RollNO = rollNO;
    }
}