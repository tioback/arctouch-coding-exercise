package com.tioback.arctouch.codingexercise;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tioback.arctouch.codingexercise.appglu.AppGlu;
import com.tioback.arctouch.codingexercise.appglu.entity.Route;
import com.tioback.arctouch.codingexercise.appglu.http.AppGluHttpClient;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSearch(View view) {
        EditText stopNameField = (EditText) findViewById(R.id.stopNameField);
        String stopName = stopNameField.getText().toString().trim();
        if (stopName.isEmpty()) {
            return;
        }

        AppGlu client = ServiceFactory.getAppGluClient();
        Route[] routes = client.findRoutesByStopName(stopName);
        if (routes == null || routes.length == 0) {
            showMessage(R.string.msg_unknown_stop_name);
            routes = new Route[0];
        }
        updateRouteList(routes);
    }

    private void showMessage(int messageId) {
        Toast.makeText(getApplicationContext(), getText(messageId), Toast.LENGTH_SHORT).show();
    }

    private void updateRouteList(Route[] routes) {

    }
}
