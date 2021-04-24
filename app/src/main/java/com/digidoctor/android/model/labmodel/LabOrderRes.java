package com.digidoctor.android.model.labmodel;

import java.util.List;

public class LabOrderRes {


    String responseMessage;
    Integer responseCode;
    List<OrderType> responseValue;

    public List<OrderType> getResponseValue() {
        return responseValue;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public static class OrderType {
        List<LabOrderModel> homeVisit;
        List<LabOrderModel> labVisiit;

        public List<LabOrderModel> getHomeVisit() {
            return homeVisit;
        }

        public List<LabOrderModel> getLabVisiit() {
            return labVisiit;
        }
    }

}

