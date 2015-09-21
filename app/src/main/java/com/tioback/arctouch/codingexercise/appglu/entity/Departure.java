package com.tioback.arctouch.codingexercise.appglu.entity;

/**
 * Created by renatopb on 16/09/15.
 */
public class Departure {

    private int id;
    private String calendar;
    private String time;

    public Departure() {
    }

    public Departure(int id, String calendar, String time) {
        this.id = id;
        this.calendar = calendar;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
