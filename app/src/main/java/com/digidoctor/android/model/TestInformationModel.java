package com.digidoctor.android.model;

public class TestInformationModel {

    private String testName;
    private String value;
    private String unit;
    private String range;

    private String testId;
    private String testValue;
    private String subTestId;
    private String subTestRangeId;
    private String unitId;
    private String testRemark;




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

    public String getTestName() {
        return testName;
    }

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getRange() {
        return range;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return "TestInformationModel{" +
                "testName='" + testName + '\'' +
                ", value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                ", range='" + range + '\'' +
                ", testId='" + testId + '\'' +
                ", testValue='" + testValue + '\'' +
                ", subTestId='" + subTestId + '\'' +
                ", subTestRangeId='" + subTestRangeId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", testRemark='" + testRemark + '\'' +
                '}';
    }
}
