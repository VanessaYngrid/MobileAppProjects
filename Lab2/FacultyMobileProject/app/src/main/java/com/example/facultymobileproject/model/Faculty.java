package com.example.facultymobileproject.model;

public class Faculty {

    private int f_Id;
    private String f_Lname;
    private String f_Fname;
    private String f_zipcodeBirth;
    private double f_Salary;
    private double f_bonus;


    public Faculty() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Faculty(int f_Id, String f_Lname, String f_Fname,  String f_zipcodeBirth, double f_Salary, double f_bonus) {
        super();
        this.f_Id = f_Id;
        this.f_Lname = f_Lname;
        this.f_Fname = f_Fname;
        this.f_zipcodeBirth = f_zipcodeBirth;
        this.f_Salary = f_Salary;
        this.f_bonus = f_bonus;

    }

    public int getF_Id() {
        return f_Id;
    }

    public void setF_Id(int f_Id) {
        this.f_Id = f_Id;
    }

    public String getF_Lname() {
        return f_Lname;
    }

    public void setF_Lname(String f_Lname) {
        this.f_Lname = f_Lname;
    }

    public String getF_Fname() {
        return f_Fname;
    }

    public void setF_Fname(String f_Fname) {
        this.f_Fname = f_Fname;
    }

    public double getF_Salary() {
        return f_Salary;
    }

    public void setF_Salary(double f_Salary) {
        this.f_Salary = f_Salary;
    }

    public double getF_bonus() {
        return f_bonus;
    }

    public void setF_bonus(double f_bonus) {
        this.f_bonus = f_bonus;
    }

    public String getF_zipcodeBirth() {
        return f_zipcodeBirth;
    }

    public void setF_zipcodeBirth(String f_zipcodeBirth) {
        this.f_zipcodeBirth = f_zipcodeBirth;
    }

    public double calculate_Bonus()
    {
        return f_Salary*f_bonus/100;
    }

}
