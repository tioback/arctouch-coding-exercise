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
    public Route[] findRoutesByStopName(String stopName) throws Exception {

        if (stopName.length() == 1) {
            return new Route[] {
                    new Route(22, "131", "AGRONÔMICA VIA GAMA D'EÇA", "2009-10-26T02:00:00+0000", 9),
                    new Route(32, "133", "AGRONÔMICA VIA MAURO RAMOS", "2012-07-23T03:00:00+0000", 9)
            };
        }

        return new Route[] {
                new Route(1, "131", "AGRONÔMICA VIA BEIRA MAR", "2009-12-29T02:00:00+0000", 9),
                new Route(2, "132", "AGRONÔMICA VIA GAMA D'EÇA", "2009-10-26T02:00:00+0000", 9),
                new Route(3, "133", "AGRONÔMICA VIA MAURO RAMOS", "2012-07-23T03:00:00+0000", 9)
        };
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
