package hr;

import java.time.LocalDate;

public interface IPerson {
    String getId();

    IAddress getAddress();

    String getName();

    LocalDate getBirthDate();
}

