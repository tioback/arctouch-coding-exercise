package com.tioback.arctouch.codingexercise;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tioback.arctouch.codingexercise.appglu.AppGlu;
import com.tioback.arctouch.codingexercise.appglu.entity.Route;
import com.tioback.arctouch.codingexercise.appglu.http.AppGluHttpClient;

import java.io.Serializable;
import java.text.ParseException;

public class ListActivity extends ProtoActivity {
    private Button search;
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
        configureRoutesList();
    }

    private void configureSearchButton() {
        search = (Button) findViewById(R.id.button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText stopNameField = (EditText) findViewById(R.id.stopNameField);
                String stopName = stopNameField.getText().toString().trim();
                if (stopName.isEmpty()) {
                    showMessage(R.string.msg_inform_stop_name);
                    return;
                }

                Route[] routes = getRoutes(stopName);
                if (routes == null || routes.length == 0) {
                    showMessage(R.string.msg_unknown_stop_name);
                    return;
                }

                updateRouteList(routes);
            }
        });
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
                Intent detail = new Intent(getApplicationContext(), DetailActivity.class);
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
