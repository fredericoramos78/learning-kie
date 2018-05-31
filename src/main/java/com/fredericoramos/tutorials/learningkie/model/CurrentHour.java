package com.fredericoramos.tutorials.learningkie.model;

import java.util.Calendar;

public class CurrentHour {

    private int hour;


    public CurrentHour() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -7);
        this.hour = cal.getTime().getHours();
    }

    public int getHour() {
        return hour;
    }
    
    @Override
    public String toString() {
        return "(Clock is now at " + this.hour +"hrs)";
    }
}
