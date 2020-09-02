package transport;

import hr.LicenseType;

import java.util.Arrays;

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
        LicenseType[] tempLicense = new LicenseType[15]; //maybe por um valor mais baixo...
        int indexTemp = 0;
        try {
            for( int i=0 ; i<NeededLicenses.length ; i++){
                tempLicense[indexTemp++]=NeededLicenses[i];
            }
            Arrays.sort(tempLicense);
            return tempLicense;
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Array index out of bounds...");
        }
        return null;
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
        TransportationTypes[] tempTT = new TransportationTypes[4]; //maybe por um valor mais baixo...
        int indexTemp = 0;
        try {
            for( int i=0 ; i<TransportationTypes.length ; i++){
                tempTT[indexTemp++]=TransportationTypes[i];
            }
            return tempTT;
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Array index out of bounds...");
        }
        return null;
    }
}
