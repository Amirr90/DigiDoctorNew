package com.digidoctor.android.model.labmodel;

public class labmodel {

    String memberId;

    @Override
    public String toString() {
        return "labmodel{" +
                "memberId='" + memberId + '\'' +
                '}';
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
