package com.example.teamtrackingapptest2;

public class Student {

    String id;
    String Name;
    String POT;
    String Branch;
    String Year;
    String DateOfJoin;
    String Projects;

    public Student(){}

    public Student(String id, String name, String POT, String branch, String year, String dateOfJoin, String projects) {
        this.id = id;
        Name = name;
        this.POT = POT;
        Branch = branch;
        Year = year;
        DateOfJoin = dateOfJoin;
        Projects = projects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPOT() {
        return POT;
    }

    public void setPOT(String POT) {
        this.POT = POT;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getDateOfJoin() {
        return DateOfJoin;
    }

    public void setDateOfJoin(String dateOfJoin) {
        DateOfJoin = dateOfJoin;
    }

    public String getProjects() {
        return Projects;
    }

    public void setProjects(String projects) {
        Projects = projects;
    }
}