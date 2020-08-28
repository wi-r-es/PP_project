package hr;

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
}
