package transport;

import hr.LicenseType;

public interface IVehicle {
    LicenseType[] getAllowedLicenses();

    String getLicensePlate();

    IBox getCargoBox();

    double getMaxWeight();

    VehicleStatus getStatus();

    void setStatus(VehicleStatus var1);

    TransportationTypes[] getTransportationTypes();
}

