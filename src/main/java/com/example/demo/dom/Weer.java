package com.example.demo.dom;

public class Weer {

    private double temperatuur;

    private double gevoelstemperatuur;

    private double wind;

    private String advies;
    private String beschrijving;

    public double getTemperatuur() {
        return temperatuur;
    }

    public void setTemperatuur(double temperatuur) {
        this.temperatuur = temperatuur;
    }

    public double getGevoelstemperatuur() {
        return gevoelstemperatuur;
    }

    public void setGevoelstemperatuur(double gevoelstemperatuur) {
        this.gevoelstemperatuur = gevoelstemperatuur;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getAdvies() {
        return advies;
    }

    public void setAdvies(String advies) {
        this.advies = advies;
    }
}
