package com.tioback.arctouch.codingexercise.appglu;

import com.tioback.arctouch.codingexercise.appglu.entity.Departure;
import com.tioback.arctouch.codingexercise.appglu.entity.Route;
import com.tioback.arctouch.codingexercise.appglu.entity.Stop;

/**
 * Created by renatopb on 16/09/15.
 */
public interface AppGluClient {

    Route[] findRoutesByStopName(String stopName) throws Exception;

    Stop[] findStopsByRouteId(int routeId) throws Exception;

    Departure[] findDeparturesByRouteId(int routeId) throws Exception;
}
