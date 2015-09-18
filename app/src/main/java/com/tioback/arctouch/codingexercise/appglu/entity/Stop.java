package com.tioback.arctouch.codingexercise.appglu.entity;

/**
 * Created by renatopb on 16/09/15.
 */
public class Stop {

    private int id;
    private String name;
    private int sequence;
    private int route_id;

    public Stop() {
    }

    public Stop(int id, String name, int sequence, int route_id) {
        this.id = id;
        this.name = name;
        this.sequence = sequence;
        this.route_id = route_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }
}
