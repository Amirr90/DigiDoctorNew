package com.digidoctor.android.model.patientModel;

public class HomeIsolationReqModel {
    private Integer memberId;
    private Integer hospitalId;
    private String comorbidities;
    private String currentProblem;
    private Integer packageId;
    private String testdate;
    private String allergires;
    private String lifeSupport;
    private String o2;
    private String onSetDate;
    private String dtDataTable;


    private Integer id;
    private String hospitalName;
    private String packageName;
    private Integer comoribid;
    private String stymptoms;
    private String testDate;
    private String homeIsolationStatus;
    private String userMobileNo;
    private String name;


    public Integer getId() {
        return id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Integer getComoribid() {
        return comoribid;
    }

    public String getStymptoms() {
        return stymptoms;
    }

    public String getTestDate() {
        return testDate;
    }

    public String getHomeIsolationStatus() {
        return homeIsolationStatus;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public String getName() {
        return name;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getComorbidities() {
        return comorbidities;
    }

    public void setComorbidities(String comorbidities) {
        this.comorbidities = comorbidities;
    }

    public String getCurrentProblem() {
        return currentProblem;
    }

    public void setCurrentProblem(String currentProblem) {
        this.currentProblem = currentProblem;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getTestdate() {
        return testdate;
    }

    public void setTestdate(String testdate) {
        this.testdate = testdate;
    }

    public String getAllergires() {
        return allergires;
    }

    public void setAllergires(String allergires) {
        this.allergires = allergires;
    }

    public String getLifeSupport() {
        return lifeSupport;
    }

    public void setLifeSupport(String lifeSupport) {
        this.lifeSupport = lifeSupport;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public String getOnSetDate() {
        return onSetDate;
    }

    public void setOnSetDate(String onSetDate) {
        this.onSetDate = onSetDate;
    }

    public String getDtDataTable() {
        return dtDataTable;
    }

    public void setDtDataTable(String dtDataTable) {
        this.dtDataTable = dtDataTable;
    }

    @Override
    public String toString() {
        return "{" +
                "memberId=" + memberId +
                ", hospitalId=" + hospitalId +
                ", comorbidities='" + comorbidities + '\'' +
                ", currentProblem='" + currentProblem + '\'' +
                ", packageId=" + packageId +
                ", testdate='" + testdate + '\'' +
                ", allergires='" + allergires + '\'' +
                ", lifeSupport='" + lifeSupport + '\'' +
                ", o2='" + o2 + '\'' +
                ", onSetDate='" + onSetDate + '\'' +
                ", dtDataTable='" + dtDataTable + '\'' +
                '}';
    }
}
