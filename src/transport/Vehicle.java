package transport;

import hr.LicenseType;

public class Vehicle implements IVehicle{

    private LicenseType[] NeededLicenses;
    private String LicensePlate;
    private IBox CargoBox;
    private double MaxWeight;
    private VehicleStatus VehicleStatus;
    private TransportationTypes[] TransportationTypes;


    public Vehicle(LicenseType[] neededLicenses, String licensePlate, IBox cargoBox, double maxWeight, transport.VehicleStatus vehicleStatus, transport.TransportationTypes[] transportationTypes) {
        NeededLicenses = neededLicenses;
        LicensePlate = licensePlate;
        CargoBox = cargoBox;
        MaxWeight = maxWeight;
        VehicleStatus = vehicleStatus;
        TransportationTypes = transportationTypes;
    }

    @Override
    public LicenseType[] getAllowedLicenses() {
        return new LicenseType[0];
    }

    @Override
    public String getLicensePlate() {
        return LicensePlate;
    }

    @Override
    public IBox getCargoBox() {
        return CargoBox;
    }

    public void setCargoBox(IBox cargoBox) {
        CargoBox = cargoBox;
    }

    @Override
    public double getMaxWeight() {
        return MaxWeight;
    }

    @Override
    public VehicleStatus getStatus() {
        return VehicleStatus;
    }

    @Override
    public void setStatus(VehicleStatus var1) {
        VehicleStatus = var1;
    }

    @Override
    public TransportationTypes[] getTransportationTypes() {
        return new TransportationTypes[0];
    }




}
