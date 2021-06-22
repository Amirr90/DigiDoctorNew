package com.digidoctor.android.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EraInvestigationData {


    List<PatientTestResult> investigationResult;

    public List<PatientTestResult> getInvestigationResult() {
        return investigationResult;
    }

    @NotNull
    @Override
    public String toString() {
        return "EraInvestigationData{" +
                "patientTestDate=" + investigationResult +
                '}';
    }


    public static class PatientTestResult {
        List<DtPatientResult> result;
        String billDate;

        @NotNull
        @Override
        public String toString() {
            return "PatientTestResult{" +
                    "dtPatientResult=" + result +
                    ", billDate='" + billDate + '\'' +
                    '}';
        }

        public String getBillDate() {
            return billDate;
        }

        public List<DtPatientResult> getResult() {
            return result;
        }
    }

    public static class DtPatientResult {
        String itemName;
        String testDetails;

        public String getTestDetails() {
            return testDetails;
        }

        public String getItemName() {
            return itemName;
        }


        @Override
        public String toString() {
            return "DtPatientResult{" +
                    "itemName='" + itemName + '\'' +
                    ", testDetails='" + testDetails + '\'' +
                    '}';
        }
    }
}
