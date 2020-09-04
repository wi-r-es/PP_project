package hr;

import org.json.simple.JSONObject;

public class Address implements IAddress{

    private String City;
    private String Country;
    private int Number;
    private String State;
    private String Street;

    public Address(String city, String country, int number, String state, String street) {
        City = city;
        Country = country;
        Number = number;
        State = state;
        Street = street;
    }

    @Override
    public String getCity() {
        return City;
    }

    @Override
    public void setCity(String city) {
        City = city;
    }

    @Override
    public String getCountry() {
        return Country;
    }

    @Override
    public void setCountry(String country) {
        Country = country;
    }

    @Override
    public int getNumber() {
        return Number;
    }

    @Override
    public void setNumber(int number) {
        Number = number;
    }

    @Override
    public String getState() {
        return State;
    }

    @Override
    public void setState(String state) {
        State = state;
    }

    @Override
    public String getStreet() {
        return Street;
    }

    @Override
    public void setStreet(String street) {
        Street = street;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj) return true; // its himself
        if( (obj == null) || (this.getClass() != obj.getClass()) ){
            return false;
        }//confirmar se parametro e da mesma classe do recetor
        Address a = (Address) obj;
        return ( City.equals(a.getCity()) && Country.equals(a.getCountry()) && Number==a.getNumber() && State.equals(a.getState()) && Street.equals(a.getStreet()) );
    }
    @Override
    public JSONObject getObject(){
        JSONObject o1 = new JSONObject();
        o1.put("city", this.City);
        o1.put("country" , this.Country);
        o1.put("number", this.Number );
        o1.put("state", this.State);
        o1.put("street", this.Street);
        return o1;

    }
}
