package hr;

public class Destination implements IDestination {

    private String Name;
    private IAddress Address;
    private IGeoCoordinates GeoCoordinates;

    public Destination(String name, IAddress address, IGeoCoordinates geoCoordinates) {
        Name = name;
        Address = address;
        GeoCoordinates = geoCoordinates;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public IAddress getAddress() {
        return Address;
    }

    @Override
    public IGeoCoordinates getGeoCoordinates() {
        return GeoCoordinates;
    }
}
