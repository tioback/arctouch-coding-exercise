package com.tioback.arctouch.codingexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.tioback.arctouch.codingexercise.appglu.entity.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends ProtoActivity {

    public static final String ROUTE = "detail_activity_route_id";

    private TextView routeName;
    private ExpandableListView streets;
    private ExpandableListView timetable;

    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        loadBundle(savedInstanceState);
        configureFieldsBehaviors();
    }

    private void loadBundle(Bundle bundle) {
        route = (Route) getIntent().getSerializableExtra(ROUTE);
        if (route == null) {
            throw new IllegalArgumentException(getString(R.string.msg_missing_route));
        }
    }

    private void configureFieldsBehaviors() {
        configureRouteName();
        configureStreets();
        configureTimeTable();
    }

    private void configureRouteName() {
        routeName = (TextView) findViewById(R.id.routeName);
        routeName.setText(route.getLongName());
    }

    private void configureStreets() {
        streets = (ExpandableListView) findViewById(R.id.streets);

        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>() {{
            add(new HashMap<String, String>() {{
                put("ROOT_NAME", "Group 1");
            }});
            add(new HashMap<String, String>() {{
                put("ROOT_NAME", "Group 2");
            }});
        }};

        List<List<Map<String, String>>> listOfChildGroups = new ArrayList<>();

        List<Map<String, String>> childGroupForFirstGroupRow = new ArrayList<Map<String, String>>(){{
            add(new HashMap<String, String>() {{
                put("CHILD_NAME", "child in group 1");
            }});
            add(new HashMap<String, String>() {{
                put("CHILD_NAME", "child in group 1");
            }});
        }};
        listOfChildGroups.add(childGroupForFirstGroupRow);

        List<Map<String, String>> childGroupForSecondGroupRow = new ArrayList<Map<String, String>>(){{
            add(new HashMap<String, String>() {{
                put("CHILD_NAME", "child in group 2");
            }});
            add(new HashMap<String, String>() {{
                put("CHILD_NAME", "child in group 2");
            }});
        }};
        listOfChildGroups.add(childGroupForSecondGroupRow);

        streets.setAdapter(
                new SimpleExpandableListAdapter(
                        this,

                        groupData,
                        android.R.layout.simple_expandable_list_item_1,
                        new String[] { "ROOT_NAME" },
                        new int[] { android.R.id.text1 },

                        listOfChildGroups,
                        android.R.layout.simple_expandable_list_item_2,
                        new String[] { "CHILD_NAME", "CHILD_NAME" },
                        new int[] { android.R.id.text1, android.R.id.text2 }
                )
        );
    }

    private String[] fetchStreets() {
        // TODO [2015-09-16] [renatop] Switch to AppGlu HTTP request.
        return new String[] {"Av. Rio Branco", "Av. Mauro Ramos", "Av. Madre Benvenuta"};
    }

    private void configureTimeTable() {
        timetable = (ExpandableListView) findViewById(R.id.timetable);
//        streets.setAdapter(new SimpleExpandableListAdapter(this, new String[] { getString(R.string.field_streets_label) }, android.R.layout.simple_expandable_list_item_1, fetchTimetable()));
    }

    private String[] fetchTimetable() {
        return new String[] {"Week days: {10:00, 16:00, 22:00}, Saturday: {11:00, 20:00}, Sunday: {14:00}"};
    }

    @Override
    public int getMenuId() {
        return R.menu.menu_detail;
    }
}
