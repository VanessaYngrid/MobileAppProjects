package com.example.temperatureproject.model;

public class TemperatureConversion {

    private double fahrenheitTemp;

    public TemperatureConversion(double fahrenheitTemp) {
        this.fahrenheitTemp = fahrenheitTemp;
    }

    public double getFahrenheitTemp() {
        return fahrenheitTemp;
    }

    public void setFahrenheitTemp(double fahrenheitTemp) {
        this.fahrenheitTemp = fahrenheitTemp;
    }

    public double tempConversion()
    {
        return ((fahrenheitTemp - 32) * (5.0/9.0));
    }

    public String conversionString() {
        return String.format("Temperature in Degree is: %.2f", tempConversion());
    }
}
