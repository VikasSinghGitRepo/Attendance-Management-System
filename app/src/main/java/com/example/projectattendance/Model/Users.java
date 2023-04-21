package com.example.projectattendance.Model;

public class Users {
    String profilePic,userName,mail,password,userId;

    public Users(String profilePic, String userName, String mail, String password, String userId) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }


    //empty constructor which would be used by firebase
    public Users(){}

    //signup constructor
    public Users(String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

    public Users(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }


    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



}

