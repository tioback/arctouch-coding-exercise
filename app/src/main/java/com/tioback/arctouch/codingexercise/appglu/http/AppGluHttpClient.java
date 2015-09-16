package com.tioback.arctouch.codingexercise.appglu.http;

import com.tioback.arctouch.codingexercise.appglu.AppGlu;
import com.tioback.arctouch.codingexercise.appglu.entity.Departure;
import com.tioback.arctouch.codingexercise.appglu.entity.Route;
import com.tioback.arctouch.codingexercise.appglu.entity.Stop;

/**
 * Created by renatopb on 16/09/15.
 */
public class AppGluHttpClient implements AppGlu {
    @Override
    public Route[] findRoutesByStopName(String stopName) {
        return new Route[0];
    }

    @Override
    public Stop[] findStopsByRouteId(int routeId) {
        return new Stop[0];
    }

    @Override
    public Departure[] findDeparturesByRouteId(int routeId) {
        return new Departure[0];
    }
}
