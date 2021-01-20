package com.digidoctor.android.model;

public class TimeModel {
    String dayName;
    String timeFrom;
    String timeTo;

    public String getDayName() {
        return dayName;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public TimeModel(String dayName, String timeFrom, String timeTo) {
        this.dayName = dayName;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }
}
