package hr;

import org.json.simple.JSONObject;

public interface ICustomer extends IPerson {
    String getVat();
    JSONObject getObject();
}

