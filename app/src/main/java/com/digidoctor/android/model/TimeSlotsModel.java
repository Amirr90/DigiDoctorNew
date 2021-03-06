package com.digidoctor.android.model;

import java.util.List;

public class TimeSlotsModel {

    String dayName;
    List<TimeDetailsModel> timeDetails;

    public String getDayName() {
        return dayName;
    }

    public List<TimeDetailsModel> getTimeDetails() {
        return timeDetails;
    }

    public static class TimeDetailsModel {
        String timeFrom;
        String timeTo;

        public String getTimeFrom() {
            return timeFrom;
        }

        public String getTimeTo() {
            return timeTo;
        }
    }
}
