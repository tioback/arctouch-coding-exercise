package com.tioback.arctouch.codingexercise.appglu.mock;

import com.tioback.arctouch.codingexercise.appglu.AppGluClient;
import com.tioback.arctouch.codingexercise.appglu.entity.Departure;
import com.tioback.arctouch.codingexercise.appglu.entity.Route;
import com.tioback.arctouch.codingexercise.appglu.entity.Stop;

/**
 * Created by renatopb on 18/09/15.
 */
public class AppGluFixedDataClient implements AppGluClient {
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
        return new Stop[] {
                new Stop(1, "Av. Rio Branco", 1, 1),
                new Stop(2, "Av. Mauro Ramos", 2, 1),
                new Stop(3, "Av. Madre Benvenuta", 3, 1)
        };
    }

    @Override
    public Departure[] findDeparturesByRouteId(int routeId) {
        return new Departure[] {
                new Departure(1, "WEEKDAY", "10:00"),
                new Departure(2, "WEEKDAY", "16:00"),
                new Departure(3, "WEEKDAY", "22:00"),
                new Departure(4, "SATURDAY", "11:00"),
                new Departure(5, "SATURDAY", "20:00"),
                new Departure(6, "SUNDAY", "14:00")
        };
    }
}
