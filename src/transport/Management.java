package transport;

import exceptions.DeliveryException;
import exceptions.ManagementException;
import hr.ICustomer;
import hr.IDestination;
import hr.IDriver;

public class Management implements IManagement {


    @Override
    public boolean addItem(IItem var1) throws ManagementException {
        return false;
    }

    @Override
    public boolean removeItem(IItem var1) throws ManagementException {
        return false;
    }

    @Override
    public IItem[] getItems() {
        return new IItem[0];
    }

    @Override
    public IItem[] getItems(ICustomer var1) {
        return new IItem[0];
    }

    @Override
    public IItem[] getItems(IDestination var1) {
        return new IItem[0];
    }

    @Override
    public IItem[] getItems(TransportationTypes var1) {
        return new IItem[0];
    }

    @Override
    public IItem[] getItems(ItemStatus var1) {
        return new IItem[0];
    }

    @Override
    public boolean addVehicle(IVehicle var1) throws ManagementException {
        return false;
    }

    @Override
    public boolean addDriver(IDriver var1) throws ManagementException {
        return false;
    }

    @Override
    public boolean removeDriver(IDriver var1) throws ManagementException {
        return false;
    }

    @Override
    public boolean removeVehicle(IVehicle var1) throws ManagementException {
        return false;
    }

    @Override
    public IVehicle[] getFleet() {
        return new IVehicle[0];
    }

    @Override
    public IVehicle[] getFleet(VehicleStatus var1) {
        return new IVehicle[0];
    }

    @Override
    public IVehicle[] getFleet(TransportationTypes var1) {
        return new IVehicle[0];
    }

    @Override
    public IVehicle[] getFleet(VehicleStatus var1, TransportationTypes var2) {
        return new IVehicle[0];
    }

    @Override
    public boolean addDelivery(IDelivery var1) throws ManagementException {
        return false;
    }

    @Override
    public void deliveredItem(String var1, String var2) throws Exception {

    }

    @Override
    public void deliveredItem(String var1, IDestination var2) throws Exception {

    }

    @Override
    public ItemStatus checkItemStatus(String var1) throws Exception {
        return null;
    }

    @Override
    public void startDelivery(String var1) throws DeliveryException {

    }

    @Override
    public void stopDelivery(String var1) throws DeliveryException {

    }
}
