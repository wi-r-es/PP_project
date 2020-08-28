package hr;

import exceptions.HRException;
import transport.DriverStatus;

import java.time.LocalDate;

public class Driver extends Person implements IDriver{

    private LocalDate StartingPositionDate;
    private LicenseType[] LicenseType;
    private DriverStatus Status;

    public Driver(String id, String name, LocalDate birthDate, IAddress address, LocalDate startingPositionDate, hr.LicenseType[] licenseType, DriverStatus status) {
        super(id, name, birthDate, address);
        StartingPositionDate = startingPositionDate;
        LicenseType = null;
        Status = status;
    }

    @Override
    public LocalDate getStartingPositionDate() {
        return StartingPositionDate;
    }

    @Override
    public boolean addLicense(LicenseType var1) throws HRException {
        /* podes me fazer so o codigo desta que eu nao sei bem como fazer, que eu depois faco para os outros conforme o objetivo? */
        return false;
    }

    @Override
    public boolean removeLicense(LicenseType var1) throws HRException {
        return false;
    }

    @Override
    public boolean haveLicense(LicenseType var1) {
        return false;
    }

    @Override
    public DriverStatus getStatus() {
        return Status;
    }

    @Override
    public void setStatus(DriverStatus var1) {
        Status = var1;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public IAddress getAddress() {
        return super.getAddress();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public LocalDate getBirthDate() {
        return super.getBirthDate();
    }
}
