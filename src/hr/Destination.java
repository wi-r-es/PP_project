/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package hr;


import org.json.simple.JSONObject;

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

    @Override
    public boolean equals(Object obj) {
        if ( this == obj) return true; // its himself
        if( (obj == null) || (this.getClass() != obj.getClass()) ){
            return false;
        }//confirmar se parametro e da mesma classe do recetor
        Destination d = (Destination) obj;
        return ( Name.equals(d.getName()) && Address.equals(d.getAddress()) && GeoCoordinates.equals(d.getGeoCoordinates()) );
    }

    @Override
    public JSONObject getObject(){
        JSONObject o1 = new JSONObject();
        o1.put("name", this.Name);
        o1.put("address" , this.Address.getObject() );
        o1.put("geocoordinates", this.GeoCoordinates.getObject() );
        return o1;

    }
}
