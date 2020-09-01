package hr;

import java.time.LocalDate;

public class Person implements IPerson {
    private final Integer Id;
    private final String Name;
    private final LocalDate BirthDate;
    private IAddress Address;

    public Person(Integer id, String name, LocalDate birthDate, IAddress address) {
        Id = id;
        Name = name;
        BirthDate = birthDate;
        Address = address;
    }

    @Override
    public String getId() {
        return Integer.toString(Id);
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public LocalDate getBirthDate() {
        return BirthDate;
    }

    @Override
    public IAddress getAddress() {
        return Address;
    }

}
