package com.sms;

import java.io.Serializable;

public class Lecturer implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private int lecturerId;

    public Lecturer() {}

    public Lecturer(String username, String password, int lecturerId) {
        this.username = username;
        this.password = password;
        this.lecturerId = lecturerId;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getLecturerId() { return lecturerId; }
    public void setLecturerId(int lecturerId) { this.lecturerId = lecturerId; }
}