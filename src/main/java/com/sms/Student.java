package com.sms;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String section;
    private int id;

    public Student() {}

    public Student(String username, String password, String section, int id) {
        this.username = username;
        this.password = password;
        this.section = section;
        this.id = id;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}
