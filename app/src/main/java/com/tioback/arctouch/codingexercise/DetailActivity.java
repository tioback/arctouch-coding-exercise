package com.tioback.arctouch.codingexercise;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.tioback.arctouch.codingexercise.appglu.entity.DayType;
import com.tioback.arctouch.codingexercise.appglu.entity.Departure;
import com.tioback.arctouch.codingexercise.appglu.entity.Route;
import com.tioback.arctouch.codingexercise.appglu.entity.Stop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends ProtoActivity {

    public static final String ROUTE = "detail_activity_route_id";
    public static final String GROUP_KEY = "ROOT_NAME";
    public static final String ITEM_KEY = "CHILD_NAME";

    private TextView routeName;
    private ExpandableListView _streets;
    private ExpandableListView _timetable;

    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        loadBundle(savedInstanceState);
        try {
            configureFieldsBehaviors();
        } catch (Exception e) {
            Log.e(this.getClass().getName(), "Error configuring fields.");
            finish();
        }
    }

    private void loadBundle(Bundle bundle) {
        route = (Route) getIntent().getSerializableExtra(ROUTE);
        if (route == null) {
            throw new IllegalArgumentException(getString(R.string.msg_missing_route));
        }
    }

    private void configureFieldsBehaviors() throws Exception {
        configureBackButton();
        configureRouteName();
        configureStreets();
        configureTimeTable();
    }

    private void configureBackButton() {
        ((Button)findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void configureRouteName() {
        routeName = (TextView) findViewById(R.id.routeName);
        routeName.setText(route.getName());
    }

    private void configureStreets() throws Exception {
        _streets = (ExpandableListView) findViewById(R.id.streets);

        List<Map<String, String>> groupsNames = new ArrayList<Map<String, String>>() {{
            add(new HashMap<String, String>() {{
                put(GROUP_KEY, getString(R.string.field_streets_label));
            }});
        }};

        String[] streets = fetchStreets();
        List<List<Map<String, String>>> groupsItems = new ArrayList<>();
        List<Map<String, String>> items = new ArrayList<>();
        for (final String street : streets) {
            items.add(new HashMap<String, String>() {{
                put(ITEM_KEY, street);
            }});
        }
        groupsItems.add(items);

        _streets.setAdapter(new SimpleExpandableListAdapter(
            this,

            groupsNames,
            android.R.layout.simple_expandable_list_item_1,
            new String[]{GROUP_KEY},
            new int[]{android.R.id.text1},

            groupsItems,
            android.R.layout.simple_expandable_list_item_1,
            new String[]{ITEM_KEY},
            new int[]{android.R.id.text1}
        ));
    }

    private String[] fetchStreets() throws Exception {
        List<String> result = new ArrayList<String>();
        Stop[] stops = ServiceFactory.getAppGluClient().findStopsByRouteId(route.getId());
        if (stops == null || stops.length == 0) {
            Log.i(this.getClass().getName(), "No stops found for route: " + route);
            return new String[0];
        }

        for (Stop stop : stops) {
            result.add(stop.getName());
        }

        return result.toArray(new String[0]);
    }

    private void configureTimeTable() throws Exception {
        _timetable = (ExpandableListView) findViewById(R.id.timetable);

        final Map<String, List<String>> timetable = fetchTimetable();

        List<Map<String, String>> groupsNames = new ArrayList<>();
        List<List<Map<String, String>>> groupsItems = new ArrayList<>();
        addTimesByType(DayType.WEEKDAY, timetable, groupsNames, groupsItems);
        addTimesByType(DayType.SATURDAY, timetable, groupsNames, groupsItems);
        addTimesByType(DayType.SUNDAY, timetable, groupsNames, groupsItems);


        _timetable.setAdapter(new SimpleExpandableListAdapter(
            this,

            groupsNames,
            android.R.layout.simple_expandable_list_item_1,
            new String[]{GROUP_KEY},
            new int[]{android.R.id.text1},

            groupsItems,
            android.R.layout.simple_expandable_list_item_1,
            new String[]{ITEM_KEY},
            new int[]{android.R.id.text1}
        ));
    }

    private Map<String, List<String>> fetchTimetable() throws Exception {
        Map<String, List<String>> result = new HashMap<>();

        Departure[] departures = ServiceFactory.getAppGluClient().findDeparturesByRouteId(route.getId());
        if (departures == null || departures.length == 0) {
            Log.i(this.getClass().getName(), "No departures found for route: " + route);
            return result;
        }

        for (Departure departure : departures) {
            List<String> times = result.get(departure.getCalendar());
            if (times == null) {
                times = new ArrayList<String>();
                result.put(departure.getCalendar(), times);
            }
            times.add(departure.getTime());
        }
        return result;
    }

    private void addTimesByType(DayType dayType, final Map<String, List<String>> timetable, List<Map<String, String>> groupsNames, List<List<Map<String, String>>> groupsItems) {
        final String type = dayType.name();
        if (!timetable.containsKey(type)) {
            return;
        }

        groupsNames.add(new HashMap<String, String>() {{
            put(GROUP_KEY, type);
        }});

        List<Map<String, String>> items = new ArrayList<Map<String, String>>() {{
            for (final String time : timetable.get(type)) {
                add(new HashMap<String, String>() {{
                    put(ITEM_KEY, time);
                }});
            }
        }};
        groupsItems.add(items);
    }

    @Override
    public int getMenuId() {
        return R.menu.menu_detail;
    }
}
