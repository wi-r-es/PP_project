/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package hr;


import exceptions.HRException;
import java.time.LocalDate;
import transport.DriverStatus;

public interface IDriver extends IPerson {
    LocalDate getStartingPositionDate();

    boolean addLicense(LicenseType var1) throws HRException;

    boolean removeLicense(LicenseType var1) throws HRException;

    boolean haveLicense(LicenseType var1);

    DriverStatus getStatus();

    void setStatus(DriverStatus var1);
}

