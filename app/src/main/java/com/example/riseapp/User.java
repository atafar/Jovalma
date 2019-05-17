package com.example.riseapp;

public class User {

    private String email;
    private String name;
    private String gender;
    private String city;
    private String date;
    private String registerDate;

    public User() {
    }

    public User(String email, String name, String gender, String city, String date, String registerDate) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.date = date;
        this.registerDate = registerDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}
