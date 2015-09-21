package com.tioback.arctouch.codingexercise;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements StreetSelectDialogListener {

    public static final String STREET_NAME = "street_name";
    public static final String PICKED_STREET = "picked_street";
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap != null) {
            return;
        }


        // Try to obtain the map from the SupportMapFragment.
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            setUpMap();
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */

    private static final LatLng ARC_TOUCH = new LatLng(-27.596003, -48.520796);
    private static final int MAX_RESULTS = 1;
    private void setUpMap() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ARC_TOUCH, 13));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String street = getClosestStreet(latLng);
                if (street == null) {
                    return;
                }

                street = removeStreetTypeAndAbbreviations(street);
                getIntent().putExtra(STREET_NAME, street);
                confirm(street);
            }
        });
    }

    private String removeStreetTypeAndAbbreviations(String street) {
        return street.replaceFirst("[^ ]+? ", "").replaceAll("[^\\.]+?\\. ?", "");
    }

    private String getClosestStreet(LatLng latLng) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.forLanguageTag("pt-BR"));
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, MAX_RESULTS);
            if (addresses.isEmpty()) {
                return null;
            }

            return addresses.get(0).getThoroughfare();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), R.string.msg_error_getting_street, Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void confirm(String street) {
        if (street == null || street.trim().isEmpty()) {
            return;
        }

        SelectStreetDialog dialog = new SelectStreetDialog();
        Bundle args = new Bundle();
        args.putString(SelectStreetDialog.STREET_NAME, street);
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "dialog_id_street_selection");
    }

    @Override
    public void onStreetSelect() {
        Intent result = new Intent();
        result.putExtra(STREET_NAME, getIntent().getStringExtra(STREET_NAME));
        result.putExtra(PICKED_STREET, true);
        setResult(RESULT_OK, result);
        finish();
    }
}
