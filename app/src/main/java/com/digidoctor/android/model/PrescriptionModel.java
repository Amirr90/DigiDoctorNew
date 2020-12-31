package com.digidoctor.android.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class PrescriptionModel extends BaseObservable {

    public String memberId;
    public String problemId;
    public String problemName;
    public String serviceProviderDetailsId;
    public String serviceProviderName;
    public String startDate;
    public String filePath;
    public String dtDataTable;
    public String dtFileDataTable;
    public String drName;
    public String diagnosis;
    public String medicineName;


    @Bindable
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
        notifyPropertyChanged(BR.medicineName);
    }

    @Bindable
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Bindable
    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    @Bindable
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Bindable
    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    @Bindable
    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    @Bindable
    public String getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }

    public void setServiceProviderDetailsId(String serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    @Bindable
    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    @Bindable
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Bindable
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Bindable
    public String getDtDataTable() {
        return dtDataTable;
    }

    public void setDtDataTable(String dtDataTable) {
        this.dtDataTable = dtDataTable;
    }

    @Bindable
    public String getDtFileDataTable() {
        return dtFileDataTable;
    }

    public void setDtFileDataTable(String dtFileDataTable) {
        this.dtFileDataTable = dtFileDataTable;
    }

    private void notifyAllData() {
        notifyPropertyChanged(BR._all);
    }


    @Override
    public String toString() {
        return "PrescriptionModel{" +
                "memberId='" + memberId + '\'' +
                ", problemId='" + problemId + '\'' +
                ", problemName='" + problemName + '\'' +
                ", serviceProviderDetailsId='" + serviceProviderDetailsId + '\'' +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", filePath='" + filePath + '\'' +
                ", dtDataTable='" + dtDataTable + '\'' +
                ", dtFileDataTable='" + dtFileDataTable + '\'' +
                ", drName='" + drName + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", medicineName='" + medicineName + '\'' +
                '}';
    }
}
