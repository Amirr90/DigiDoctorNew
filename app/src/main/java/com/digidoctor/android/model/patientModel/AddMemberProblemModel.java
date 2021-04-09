package com.digidoctor.android.model.patientModel;

public class AddMemberProblemModel {
    String userMobileNo;
    String memberId;
    String problemDate;
    String dtDataTable;
    String isUpCovid;
    String problemTime;
    String alphabet;

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getProblemDate() {
        return problemDate;
    }

    public void setProblemDate(String problemDate) {
        this.problemDate = problemDate;
    }

    public String getDtDataTable() {
        return dtDataTable;
    }

    public void setDtDataTable(String dtDataTable) {
        this.dtDataTable = dtDataTable;
    }

    public String getIsUpCovid() {
        return isUpCovid;
    }

    public void setIsUpCovid(String isUpCovid) {
        this.isUpCovid = isUpCovid;
    }

    public String getProblemTime() {
        return problemTime;
    }

    public void setProblemTime(String problemTime) {
        this.problemTime = problemTime;
    }
}
