package com.tioback.arctouch.codingexercise;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tioback.arctouch.codingexercise.appglu.entity.Route;

import java.io.Serializable;

public class ListActivity extends ProtoActivity {
    private Button search;
    private Button map;
    private ListView _routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        configureElementsBehaviours();
    }

    protected int getMenuId() {
        return R.menu.menu_list;
    }

    private void configureElementsBehaviours() {
        configureSearchButton();
        configureMapButton();
        configureRoutesList();
    }

    private void configureSearchButton() {
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText stopNameField = (EditText) findViewById(R.id.stopNameField);
                String stopName = stopNameField.getText().toString().trim();
                if (stopName.isEmpty()) {
                    showMessage(R.string.msg_inform_stop_name);
                    return;
                }

                searchAndUpdateRoutes(stopName);
            }
        });
    }

    private void searchAndUpdateRoutes(String stopName) {
        Route[] routes = getRoutes(stopName);
        if (routes == null || routes.length == 0) {
            showMessage(R.string.msg_unknown_stop_name);
            return;
        }

        updateRouteList(routes);
    }

    private static final int PICK_STREET = 0;

    private void configureMapButton() {
        map = (Button) findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(mapIntent, PICK_STREET);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != PICK_STREET) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        if (!data.getBooleanExtra(MapsActivity.PICKED_STREET, false)) {
            return;
        }

        String streetName = data.getStringExtra(MapsActivity.STREET_NAME);
        if (streetName == null || streetName.trim().isEmpty()) {
            showMessage(R.string.msg_unknown_stop_name);
            return;
        }

        searchAndUpdateRoutes(streetName);
    }

    private Route[] getRoutes(String stopName) {
        try {
            return ServiceFactory.getAppGluClient().findRoutesByStopName(stopName);
        } catch (Exception e) {
            showMessage(R.string.msg_error_getting_route);
            return new Route[0];
        }
    }

    private void configureRoutesList() {
        _routes = (ListView) findViewById(R.id.routes);
        _routes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(ListActivity.this, DetailActivity.class);
                detail.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                Bundle bundle = new Bundle();
                bundle.putSerializable(DetailActivity.ROUTE, (Serializable) parent.getAdapter().getItem(position));
                detail.putExtras(bundle);
                startActivity(detail);
            }
        });
    }

    private void updateRouteList(Route[] routes) {
        _routes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, routes));
    }
}
