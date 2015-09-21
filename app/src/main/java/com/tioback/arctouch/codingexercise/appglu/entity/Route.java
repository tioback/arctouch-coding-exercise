package com.tioback.arctouch.codingexercise.appglu.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by renatopb on 16/09/15.
 */
public class Route implements Serializable {

    private int id;
    private String shortName;
    private String longName;
    private Date lastModifiedDate;
    private int agencyId;

    public Route() {}

    public Route(int id, String shortName, String longName, String lastModifiedDate, int agencyId) throws ParseException {
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
        this.lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(lastModifiedDate);
        this.agencyId = agencyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getName() {
        return shortName + " - " + longName;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public String toString() {
        return getName();
    }
}
