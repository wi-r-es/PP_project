package transport;

import hr.LicenseType;

public class Truck extends Vehicle implements ITruck {


    public Truck(LicenseType[] neededLicenses, String licensePlate, IBox cargoBox, double maxWeight, transport.VehicleStatus vehicleStatus, transport.TransportationTypes[] transportationTypes) {
        super(neededLicenses, licensePlate, cargoBox, maxWeight, vehicleStatus, transportationTypes);
    }

    @Override
    public boolean swapCargoBox(IBox var1) {
        transport.VehicleStatus temp = super.getStatus(); /* is this well coded xD ? */

        /* Swap CargoBox if status is free */
        switch(temp) {    /* Check this switch condition  */
            case FREE -> {
                setCargoBox(var1);
                return true;
            }
            default -> {
                return false;
            }

        }


    }
}
