package com.digidoctor.android.model;

public class VitalModel {
    public int sys;
    public int dia;
    public int pulseRate;
    public int temperature;
    public int randomBloodSugar;
    public int spo2;
    public int respiratoryRate;


    public int getSys() {
        return sys;
    }

    public void setSys(int sys) {
        this.sys = sys;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(int pulseRate) {
        this.pulseRate = pulseRate;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getRandomBloodSugar() {
        return randomBloodSugar;
    }

    public void setRandomBloodSugar(int randomBloodSugar) {
        this.randomBloodSugar = randomBloodSugar;
    }

    public int getSpo2() {
        return spo2;
    }

    public void setSpo2(int spo2) {
        this.spo2 = spo2;
    }

    public int getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(int respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }


    @Override
    public String toString() {
        return "VitalModel{" +
                "sys=" + sys +
                ", dia=" + dia +
                ", pulseRate=" + pulseRate +
                ", temperature=" + temperature +
                ", randomBloodSugar=" + randomBloodSugar +
                ", spo2=" + spo2 +
                ", respiratoryRate=" + respiratoryRate +
                '}';
    }
}
