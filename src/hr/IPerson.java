/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package hr;


import java.time.LocalDate;

public interface IPerson {
    String getId();

    IAddress getAddress();

    String getName();

    LocalDate getBirthDate();
}

