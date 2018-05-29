package com.loconoco.tutorials.learningkie.model;

import java.util.Calendar;

public class CurrentHour {

    private int hour;


    public CurrentHour() {
        this.hour = Calendar.getInstance().getTime().getHours();
    }

    public int getHour() {
        return hour;
    }
}
