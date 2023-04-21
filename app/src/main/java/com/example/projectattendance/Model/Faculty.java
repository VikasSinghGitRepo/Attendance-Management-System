package com.example.projectattendance.Model;

public class Faculty {
String name,id,subject,course,password;

    public Faculty(String name, String id, String subject, String password) {
        this.name = name;
        this.id = id;
        this.subject = subject;
        this.password = password;
    }

    public Faculty(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
