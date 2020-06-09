package com.example.tutor_find.Model;

public class UserInfo {

    String name;
    String institution;
    String department;
    String year;
    String email;
    String number;
    String userId;

    public UserInfo(String name, String institution, String department, String year, String email, String number, String userId) {
        this.name = name;
        this.institution = institution;
        this.department = department;
        this.year = year;
        this.email = email;
        this.number = number;
        this.userId= userId;
    }

    public UserInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
