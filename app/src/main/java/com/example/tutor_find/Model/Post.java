package com.example.tutor_find.Model;

import java.util.List;

public class Post {
    String address;
    String curriculum;
    String group;
    String salary;
    String studyClass;
    String subjectList;
    String description;
    String userId;
    String postId;
    String area;

    public Post(String address, String curriculum, String group, String salary, String studyClass, String subjectList, String description, String userId, String postId, String area) {
        this.address = address;
        this.curriculum = curriculum;
        this.group = group;
        this.salary = salary;
        this.studyClass = studyClass;
        this.subjectList = subjectList;
        this.description = description;
        this.userId = userId;
        this.postId = postId;
        this.area = area;
    }

    public Post() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStudyClass() {
        return studyClass;
    }

    public void setStudyClass(String studyClass) {
        this.studyClass = studyClass;
    }

    public String getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(String subjectList) {
        this.subjectList = subjectList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getArea() {return  area; }

    public void setArea(String area) { this.area = area; }
}
