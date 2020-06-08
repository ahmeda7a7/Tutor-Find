package com.example.tutor_find.Model;

public class UserInfo {

    String name;
    String institution;
    String department;
    String year;
    String email;
    String number;
    String id;

    public UserInfo(String name, String institution, String department, String year, String email, String number, String id) {
        this.name = name;
        this.institution = institution;
        this.department = department;
        this.year = year;
        this.email = email;
        this.number = number;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
