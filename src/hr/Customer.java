package hr;

import org.json.simple.JSONObject;

import java.time.LocalDate;

public class Customer extends Person implements ICustomer{
    private String Vat;

    public Customer(Integer id, String name, LocalDate birthDate, IAddress address, String vat) {
        super(id, name, birthDate, address);
        Vat = vat;
    }

    @Override
    public String getVat() {
        return Vat;
    }

    @Override
    public JSONObject getObject(){
        JSONObject o1 = new JSONObject();
        o1.put("id", this.getId());
        o1.put("name", this.getName());
        o1.put("vat", this.getVat());
    }
}
