package hr;

import org.json.simple.JSONObject;

public interface IDestination {
    String getName();

    IAddress getAddress();

    IGeoCoordinates getGeoCoordinates();

    JSONObject getObject();
}

