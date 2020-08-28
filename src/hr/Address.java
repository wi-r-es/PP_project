package hr;

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
}
