package com.digidoctor.android.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.digidoctor.android.BR;

public class AddInvestigationModel extends BaseObservable {
    public String memberId;
    public String receiptNo;
    public String pathologyName;
    public String testDate;
    public String filePath;
    public String dtDataTable;
    public String dtFileDataTable;
    public String userMobileNo;
    public String remark;
    public String investigationTypeId;
    public String investigationCategoryId;

    public String testName;
    public String value;
    public String unit;
    public String range;

    public String testId;
    public String testValue;
    public String subTestId;
    public String subTestRangeId;
    public String unitId;
    public String testRemark;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public String getSubTestId() {
        return subTestId;
    }

    public void setSubTestId(String subTestId) {
        this.subTestId = subTestId;
    }

    public String getSubTestRangeId() {
        return subTestRangeId;
    }

    public void setSubTestRangeId(String subTestRangeId) {
        this.subTestRangeId = subTestRangeId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTestRemark() {
        return testRemark;
    }

    public void setTestRemark(String testRemark) {
        this.testRemark = testRemark;
    }

    @Bindable
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
        notifyPropertyChanged(BR.testName);
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        notifyPropertyChanged(BR.unit);
    }

    @Bindable
    public String getRange() {
        if (null == range) {
            range = "0";
        }
        return range;
    }

    public void setRange(String range) {
        this.range = range;
        notifyPropertyChanged(BR.range);
    }


    @Bindable
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
        notifyPropertyChanged(BR.memberId);
    }

    @Bindable
    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
        notifyPropertyChanged(BR.receiptNo);
    }

    @Bindable
    public String getPathologyName() {
        return pathologyName;
    }

    public void setPathologyName(String pathologyName) {
        this.pathologyName = pathologyName;
        notifyPropertyChanged(BR.pathologyName);
    }

    @Bindable
    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
        notifyPropertyChanged(BR.testDate);
    }

    @Bindable
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        notifyPropertyChanged(BR.filePath);
    }

    @Bindable
    public String getDtDataTable() {
        return dtDataTable;
    }

    public void setDtDataTable(String dtDataTable) {
        this.dtDataTable = dtDataTable;
        notifyPropertyChanged(BR.dtDataTable);
    }

    @Bindable
    public String getDtFileDataTable() {
        return dtFileDataTable;
    }

    public void setDtFileDataTable(String dtFileDataTable) {
        this.dtFileDataTable = dtFileDataTable;
        notifyPropertyChanged(BR.dtFileDataTable);
    }

    @Bindable
    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
        notifyPropertyChanged(BR.userMobileNo);
    }

    @Bindable
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
        notifyPropertyChanged(BR.remark);
    }

    @Bindable
    public String getInvestigationTypeId() {

        if (null == investigationTypeId)
            return "0";
        else
            return investigationTypeId;
    }

    public void setInvestigationTypeId(String investigationTypeId) {
        this.investigationTypeId = investigationTypeId;
        notifyPropertyChanged(BR.investigationTypeId);
    }

    @Bindable
    public String getInvestigationCategoryId() {
        if (null == investigationCategoryId)
            return "0";
        else
            return investigationCategoryId;
    }

    public void setInvestigationCategoryId(String investigationCategoryId) {
        this.investigationCategoryId = investigationCategoryId;
        notifyPropertyChanged(BR.investigationCategoryId);
    }


}
