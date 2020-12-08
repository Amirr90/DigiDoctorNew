package com.digidoctor.android.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.digidoctor.android.BR;


public class VitalModel extends BaseObservable {

    public String sys;
    public String dia;
    public String pulseRate;
    public String temperature;
    public String randomBloodSugar;
    public String spo2;
    public String respiratoryRate;

    private String memberId;
    private String vitalId;
    private String date;
    private String time;
    private String dtDataTable;
    private String serviceProviderDetailsID;


    @Bindable
    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
        notifyPropertyChanged(BR.sys);
    }

    @Bindable
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
        notifyPropertyChanged(BR.dia);
    }

    @Bindable
    public String getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(String pulseRate) {
        this.pulseRate = pulseRate;
        notifyPropertyChanged(BR.pulseRate);
    }

    @Bindable
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
        notifyPropertyChanged(BR.temperature);
    }

    @Bindable
    public String getRandomBloodSugar() {
        return randomBloodSugar;
    }

    public void setRandomBloodSugar(String randomBloodSugar) {
        this.randomBloodSugar = randomBloodSugar;
        notifyPropertyChanged(BR.randomBloodSugar);
    }

    @Bindable
    public String getSpo2() {
        return spo2;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
        notifyPropertyChanged(BR.spo2);
    }

    @Bindable
    public String getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(String respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
        notifyPropertyChanged(BR.respiratoryRate);
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getVitalId() {
        return vitalId;
    }

    public void setVitalId(String vitalId) {
        this.vitalId = vitalId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDtDataTable() {
        return dtDataTable;
    }

    public void setDtDataTable(String dtDataTable) {
        this.dtDataTable = dtDataTable;
    }

    public String getServiceProviderDetailsID() {
        return serviceProviderDetailsID;
    }

    public void setServiceProviderDetailsID(String serviceProviderDetailsID) {
        this.serviceProviderDetailsID = serviceProviderDetailsID;
    }


    @Override
    public String toString() {
        return "VitalModel{" +
                "sys='" + sys + '\'' +
                ", dia='" + dia + '\'' +
                ", pulseRate='" + pulseRate + '\'' +
                ", temperature='" + temperature + '\'' +
                ", randomBloodSugar='" + randomBloodSugar + '\'' +
                ", spo2='" + spo2 + '\'' +
                ", respiratoryRate='" + respiratoryRate + '\'' +
                ", memberId='" + memberId + '\'' +
                ", vitalId='" + vitalId + '\'' +
                ", problemDate='" + date + '\'' +
                ", problemTime='" + time + '\'' +
                ", dtDataTable='" + dtDataTable + '\'' +
                ", serviceProviderDetailsID='" + serviceProviderDetailsID + '\'' +
                '}';
    }
}
