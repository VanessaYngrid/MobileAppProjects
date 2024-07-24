package com.example.bookpublishingproject.model;

public class Chapter {

    private int b_id;
    private int c_no;
    private String c_title;
    private double c_price;

    public Chapter(){
        b_id = 0;
        c_no = 0;
        c_title = "";
        c_price = 0.0;
    }

    public Chapter(int b_id, int c_no, String c_title, double c_price) {
        this.b_id = b_id;
        this.c_no = c_no;
        this.c_title = c_title;
        this.c_price = c_price;
    }

    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public int getC_no() {
        return c_no;
    }

    public void setC_no(int c_no) {
        this.c_no = c_no;
    }

    public String getC_title() {
        return c_title;
    }

    public void setC_title(String c_title) {
        this.c_title = c_title;
    }

    public double getC_price() {
        return c_price;
    }

    public void setC_price(double c_price) {
        this.c_price = c_price;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "b_id=" + b_id +
                ", c_no=" + c_no +
                ", c_title='" + c_title + '\'' +
                ", c_price=" + c_price +
                '}';
    }
}
