package hr;

import org.json.simple.JSONObject;

public interface IGeoCoordinates {
    double getLatitude();

    double getLongitude();

    JSONObject getObject();
}

