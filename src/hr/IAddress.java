package hr;

import org.json.simple.JSONObject;

public interface IAddress {
    String getCity();

    String getCountry();

    int getNumber();

    String getState();

    String getStreet();

    void setCity(String var1);

    void setCountry(String var1);

    void setNumber(int var1);

    void setState(String var1);

    void setStreet(String var1);

    JSONObject getObject();
}

