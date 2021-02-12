
package com.digidoctor.android.model.pharmacyModel; ;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


 public class getfilltervarentmodel {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseValue")
    @Expose
    private List<filltervarientList> responseValue ;

     @Override
     public String toString() {
         return "getfilltervarentmodel{" +
                 "responseCode=" + responseCode +
                 ", responseMessage='" + responseMessage + '\'' +
                 ", responseValue=" + responseValue +
                 '}';
     }

     public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<filltervarientList> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<filltervarientList> responseValue) {
        this.responseValue = responseValue;
    }


     public  class filltervarientList {

         @SerializedName("id")
         @Expose
         private Integer id;
         @SerializedName("varientName")
         @Expose
         private String varientName;
         @SerializedName("varientDetails")
         @Expose
         private List<VarientDetailsList> varientDetails;

         @Override
         public String toString() {
             return "filltervarientList{" +
                     "id=" + id +
                     ", varientName='" + varientName + '\'' +
                     ", varientDetails='" + varientDetails + '\'' +
                     '}';
         }

         public Integer getId() {
             return id;
         }

         public void setId(Integer id) {
             this.id = id;
         }

         public String getVarientName() {
             return varientName;
         }

         public void setVarientName(String varientName) {
             this.varientName = varientName;
         }

         public List<VarientDetailsList> getVarientDetails() {
             return varientDetails;
         }

         public void setVarientDetails(List<VarientDetailsList> varientDetails) {
             this.varientDetails = varientDetails;
         }


         private class VarientDetailsList {



         }
     }

}

