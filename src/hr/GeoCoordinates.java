/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package hr;


import org.json.simple.JSONObject;

public class GeoCoordinates implements IGeoCoordinates{

    private double Latitude;
    private double Longitude;


    public GeoCoordinates(double latitude, double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return Latitude;
    }

    @Override
    public double getLongitude() {
        return Longitude;
    }

    @Override
    public JSONObject getObject(){
        JSONObject o1 = new JSONObject();
        o1.put("latitude", this.Latitude);
        o1.put("longitude" , this.Longitude);
        return o1;

    }
}
