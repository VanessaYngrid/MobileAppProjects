package com.example.mathoperationproject.model;

public class MathOperations {

    private double x;
    private double y;

    public MathOperations(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) { this.y = y; }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double sum()
    {
        return x + y;
    }

    public double subtraction()
    {
        return x - y;
    }

    public double multiplication()
    {
        return x * y;
    }

    public double division()
    {
        if (y == 0) {
            throw new ArithmeticException("Y have to be different than zero");
        }
        return x / y;
    }


    public String sumString() {
        return "The result of x+y is:" + sum();
    }

    public String subtractionString() {
        return "The result of x-y is:" + subtraction();
    }

    public String multiplicationString() {
        return "The result of x*y is:" + multiplication();
    }

    public String divisionString() {
        return "The result of x/y is:" + division();
    }
}
