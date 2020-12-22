package com.digidoctor.android.model;

import java.util.List;

public class InvestigationDataRes {
    int responseCode;
    String responseMessage;
    List<InvestigationDataModel> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<InvestigationDataModel> getResponseValue() {
        return responseValue;
    }

    public class InvestigationDataModel {
        List<TestNameModel> testList;
        List<UnitNameModel> unitList;

        public List<TestNameModel> getTestList() {
            return testList;
        }

        public List<UnitNameModel> getUnitList() {
            return unitList;
        }
    }

    public class TestNameModel {

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class UnitNameModel {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
