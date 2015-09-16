package com.tioback.arctouch.codingexercise;

import com.tioback.arctouch.codingexercise.appglu.AppGlu;
import com.tioback.arctouch.codingexercise.appglu.http.AppGluHttpClient;

/**
 * Created by renatopb on 16/09/15.
 */
public class ServiceFactory {
    public static AppGlu getAppGluClient() {
        return new AppGluHttpClient();
    }
}
