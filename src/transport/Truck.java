/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package transport;


import hr.LicenseType;

public class Truck extends Vehicle implements ITruck {


    public Truck(LicenseType[] neededLicenses, String licensePlate, IBox cargoBox, double maxWeight, transport.VehicleStatus vehicleStatus, transport.TransportationTypes[] transportationTypes) {
        super(neededLicenses, licensePlate, cargoBox, maxWeight, vehicleStatus, transportationTypes);
    }



    @Override
    public boolean swapCargoBox(IBox var1) {
        if ( this.getStatus().equals(VehicleStatus.FREE )){
            this.setCargoBox(var1);
            return true;
        } else return false;
    }
}

