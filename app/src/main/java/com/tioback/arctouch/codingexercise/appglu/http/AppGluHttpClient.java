package com.tioback.arctouch.codingexercise.appglu.http;

import android.os.AsyncTask;
import android.util.Log;

import com.tioback.arctouch.codingexercise.appglu.AppGluClient;
import com.tioback.arctouch.codingexercise.appglu.entity.Departure;
import com.tioback.arctouch.codingexercise.appglu.entity.Route;
import com.tioback.arctouch.codingexercise.appglu.entity.Stop;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by renatopb on 16/09/15.
 */
public class AppGluHttpClient implements AppGluClient {
    @Override
    public Route[] findRoutesByStopName(String stopName) throws Exception {
        String methodName = "findRoutesByStopName";
        String paramName = "stopName";
        String paramValue = "\"%" + stopName + "%\"";
        String entityType = "ROUTES";

        String response = new AppGluRequest().execute(methodName, paramName, paramValue, entityType).get();
        JSONObject json = new JSONObject(response);
        JSONArray rows = json.getJSONArray("rows");
        List<Route> result = new ArrayList<>(rows.length());
        for (int i = 0; i < rows.length(); i++) {
            JSONObject row = rows.getJSONObject(i);
            result.add(new Route(row.getInt("id"), row.getString("shortName"), row.getString("longName"), row.getString("lastModifiedDate"), row.getInt("agencyId")));
        }
        return result.toArray(new Route[0]);
    }

    @Override
    public Stop[] findStopsByRouteId(int routeId) throws Exception {
        String methodName = "findStopsByRouteId";
        String paramName = "routeId";
        String paramValue = Integer.toString(routeId);
        String entityType = "STOPS";

        String response = new AppGluRequest().execute(methodName, paramName, paramValue, entityType).get();
        JSONObject json = new JSONObject(response);
        JSONArray rows = json.getJSONArray("rows");
        List<Stop> result = new ArrayList<>(rows.length());
        for (int i = 0; i < rows.length(); i++) {
            JSONObject row = rows.getJSONObject(i);
            result.add(new Stop(row.getInt("id"), row.getString("name"), row.getInt("sequence"), row.getInt("route_id")));
        }
        return result.toArray(new Stop[0]);
    }

    @Override
    public Departure[] findDeparturesByRouteId(int routeId) throws Exception {
        String methodName = "findDeparturesByRouteId";
        String paramName = "routeId";
        String paramValue = Integer.toString(routeId);
        String entityType = "DEPARTURES";

        String response = new AppGluRequest().execute(methodName, paramName, paramValue, entityType).get();
        JSONObject json = new JSONObject(response);
        JSONArray rows = json.getJSONArray("rows");
        List<Departure> result = new ArrayList<>(rows.length());
        for (int i = 0; i < rows.length(); i++) {
            JSONObject row = rows.getJSONObject(i);
            result.add(new Departure(row.getInt("id"), row.getString("calendar"), row.getString("time")));
        }
        return result.toArray(new Departure[0]);
    }

    private class AppGluRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String methodName = params[0];
            String paramName = params[1];
            String paramValue = params[2];
            String entityType = params[3];


            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("WKD4N7YMA1uiM8V", "DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68".toCharArray());
                }
            });

            HttpsURLConnection urlConnection = null;
            try {
                URL url = new URL("https://api.appglu.com/v1/queries/" + methodName + "/run");
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("X-AppGlu-Environment", "staging");

                writeParams(urlConnection.getOutputStream(), paramName, paramValue);

                int statusCode = urlConnection.getResponseCode();
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    Log.e(this.getClass().getName(), "Error while fetching " + entityType + ": response not OK.");
                    throw new IllegalStateException("Response not OK");
                }

                return readResponse(new BufferedInputStream(urlConnection.getInputStream()));
            } catch (IOException e) {
                throw new IllegalStateException("Error while fetching " + entityType);
            } finally {
                urlConnection.disconnect();
            }
        }

        private void writeParams(OutputStream out, String paramName, String paramValue) {
            try {
                DataOutputStream dos = new DataOutputStream(out);
                dos.writeBytes("{\"params\":{\"" + paramName + "\":" + paramValue + "}}");
                dos.flush();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String readResponse(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }
}
