package com.example.projectattendance.Model;

public class studentUser {
    String roll_no;
    String DOB,Sem,PursuingYear,Branch,id;
    String password;

    public studentUser(){

    }
    public studentUser(String roll_no, String DOB, String sem, String pursuingYear, String branch,String id) {

        this.roll_no = roll_no;
        this.DOB = DOB;
        this.Sem = sem;
        this.PursuingYear = pursuingYear;
        this.Branch = branch;
        this.id=id;
    }

    public studentUser(String roll_no, String sem, String branch, String id,String password) {
        this.roll_no = roll_no;
        Sem = sem;
        Branch = branch;
        this.id = id;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getSem() {
        return Sem;
    }

    public void setSem(String sem) {
        Sem = sem;
    }

    public String getPursuingYear() {
        return PursuingYear;
    }

    public void setPursuingYear(String pursuingYear) {
        PursuingYear = pursuingYear;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

