package com.example.studentgradeproject.model;

public class Grade {

    private int student_id;
    private String student_lname;
    private String student_fname;
    private int s_grade_Assignment1;
    private int s_grade_Assignment2;
    private int s_grade_Assignment3;
    private int s_grade_Mid_Term;
    private int s_grade_Final_Term;

    public Grade()
    {
        student_id=0;
        student_lname="";
        student_fname="";
        s_grade_Assignment1=0;
        s_grade_Assignment2=0;
        s_grade_Assignment3=0;
        s_grade_Mid_Term=0;
        s_grade_Final_Term=0;
    }
    public Grade(int student_id, String student_lname, String student_fname, int s_grade_Assignment1, int s_grade_Assignment2, int s_grade_Assignment3, int s_grade_Mid_Term, int s_grade_Final_Term) {
        this.student_id = student_id;
        this.student_lname = student_lname;
        this.student_fname = student_fname;
        this.s_grade_Assignment1 = s_grade_Assignment1;
        this.s_grade_Assignment2 = s_grade_Assignment2;
        this.s_grade_Assignment3 = s_grade_Assignment3;
        this.s_grade_Mid_Term = s_grade_Mid_Term;
        this.s_grade_Final_Term = s_grade_Final_Term;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getStudent_lname() {
        return student_lname;
    }

    public void setStudent_lname(String student_lname) {
        this.student_lname = student_lname;
    }

    public String getStudent_fname() {
        return student_fname;
    }

    public void setStudent_fname(String student_fname) {
        this.student_fname = student_fname;
    }

    public int getS_grade_Assignment1() {
        return s_grade_Assignment1;
    }

    public void setS_grade_Assignment1(int s_grade_Assignment1) {
        this.s_grade_Assignment1 = s_grade_Assignment1;
    }

    public int getS_grade_Assignment2() {
        return s_grade_Assignment2;
    }

    public void setS_grade_Assignment2(int s_grade_Assignment2) {
        this.s_grade_Assignment2 = s_grade_Assignment2;
    }

    public int getS_grade_Assignment3() {
        return s_grade_Assignment3;
    }

    public void setS_grade_Assignment3(int s_grade_Assignment3) {
        this.s_grade_Assignment3 = s_grade_Assignment3;
    }

    public int getS_grade_Mid_Term() {
        return s_grade_Mid_Term;
    }

    public void setS_grade_Mid_Term(int s_grade_Mid_Term) {
        this.s_grade_Mid_Term = s_grade_Mid_Term;
    }

    public int getS_grade_Final_Term() {
        return s_grade_Final_Term;
    }

    public void setS_grade_Final_Term(int s_grade_Final_Term) {
        this.s_grade_Final_Term = s_grade_Final_Term;
    }

    public double calculate_GradeAverage()
    {
        double average=0.0;
        average = (((0.4 * (s_grade_Assignment1+s_grade_Assignment2+s_grade_Assignment3)/3)) + (0.3 * s_grade_Mid_Term) + (0.3 * s_grade_Final_Term));
        return average;
    }
}
