package com.example.temperatureconverter;

public class TemperatureConverter {
    public double CtF(double temp) {
        return 9 / 5 * temp + 32;
    }

    public double KtF(double temp) {
        return 9 / 5 * (temp - 273) + 32;
    }

    public double FtC(double temp) {
        return 5 / 9 * (temp - 32);
    }

    public double CtK(double temp) {
        return temp + 273;
    }

    public double KtC(double temp) {
        return temp - 273;
    }

    public double FtK(double temp) {
        return 5 / 9 * (temp - 32) + 273;
    }

}
