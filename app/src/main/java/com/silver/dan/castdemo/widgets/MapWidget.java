package com.silver.dan.castdemo.widgets;

import android.content.Context;

import com.silver.dan.castdemo.SettingEnums.MapMode;
import com.silver.dan.castdemo.SettingEnums.MapType;
import com.silver.dan.castdemo.SettingEnums.TravelMode;
import com.silver.dan.castdemo.Widget;
import com.silver.dan.castdemo.WidgetOption;
import com.silver.dan.castdemo.settingsFragments.MapSettings;
import com.silver.dan.castdemo.settingsFragments.WidgetSettingsFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MapWidget extends UIWidget {

    public MapWidget(Context context, Widget widget) {
        super(context, widget);
    }

    @Override
    public void init() {
        //https://www.google.com/maps/@47.6061734,-122.3310611,16.04z
        widget.initOption(MapSettings.LOCATION_LAT, "47.6061734");
        widget.initOption(MapSettings.LOCATION_LONG, "-122.3310611");
        widget.initOption(MapSettings.LOCATION_ADDRESS, "Seattle, Washington");
        widget.initOption(MapSettings.MAP_ZOOM, 10);
        widget.initOption(MapSettings.SHOW_TRAFFIC, false);
        widget.initOption(MapSettings.MAP_TYPE, MapType.ROADMAP.getValue());
        widget.initOption(MapSettings.MAP_MODE, MapMode.STANDARD.getValue());
        widget.initOption(MapSettings.DESTINATION_LONG, "");
        widget.initOption(MapSettings.DESTINATION_LAT, "");
        widget.initOption(MapSettings.DESTINATION_TEXT, "Not set");
        widget.initOption(MapSettings.TRAVEL_MODE, TravelMode.DRIVING.getValue());
    }

    @Override
    public JSONObject getContent() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(MapSettings.LOCATION_LAT, widget.getOption(MapSettings.LOCATION_LAT).value);
        json.put(MapSettings.LOCATION_LONG, widget.getOption(MapSettings.LOCATION_LONG).value);
        json.put(MapSettings.MAP_ZOOM, widget.getOption(MapSettings.MAP_ZOOM).getIntValue());
        json.put(MapSettings.SHOW_TRAFFIC, widget.getOption(MapSettings.SHOW_TRAFFIC).getBooleanValue());
        //for map type and transit method - send the actual text for the js api to consume
        json.put(MapSettings.MAP_TYPE, MapType.getMapType(widget.loadOrInitOption(MapSettings.MAP_TYPE, context).getIntValue()).toString());
        json.put(MapSettings.TRAVEL_MODE, TravelMode.getMode(widget.getOption(MapSettings.TRAVEL_MODE).getIntValue()).toString());

        json.put(MapSettings.MAP_MODE, widget.loadOrInitOption(MapSettings.MAP_MODE, context).getIntValue());
        json.put(MapSettings.DESTINATION_LAT, widget.getOption(MapSettings.DESTINATION_LAT).value);
        json.put(MapSettings.DESTINATION_LONG, widget.getOption(MapSettings.DESTINATION_LONG).value);
        return json;
    }

    @Override
    public WidgetSettingsFragment createSettingsFragment() {
        return new MapSettings();
    }

    @Override
    public String getWidgetPreviewSecondaryHeader() {
        WidgetOption locationAddress = widget.loadOrInitOption(MapSettings.LOCATION_ADDRESS, context);

        WidgetOption mode = widget.loadOrInitOption(MapSettings.MAP_MODE, context);
        String startingText = widget.loadOrInitOption(MapSettings.LOCATION_ADDRESS, context).value;
        if (locationAddress != null) {
            if (mode.getIntValue() == MapMode.STANDARD.getValue()) {
                return startingText;
            } else if (mode.getIntValue() == MapMode.DIRECTIONS.getValue()) {
                String transitMethod = TravelMode.getMode(widget.loadOrInitOption(MapSettings.TRAVEL_MODE, context).getIntValue()).toString().toLowerCase();
                String transitMethodCap = Character.toUpperCase(transitMethod.charAt(0)) + transitMethod.substring(1);

                String destination = widget.loadOrInitOption(MapSettings.DESTINATION_TEXT, context).value;
                return transitMethodCap + " directions from " + startingText + " to " + destination;
            }
        }


        return "Location not set";
    }
}
