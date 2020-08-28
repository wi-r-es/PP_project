package hr;

import java.time.LocalDate;

public class Customer extends Person implements ICustomer{
    private String Vat;

    public Customer(String id, String name, LocalDate birthDate, IAddress address, String vat) {
        super(id, name, birthDate, address);
        Vat = vat;
    }

    @Override
    public String getVat() {
        return Vat;
    }
}
