package transport;

import exceptions.DeliveryException;
import hr.IDestination;
import hr.IDriver;

import java.io.IOException;

public class Delivery implements IDelivery{

    private Integer Id;
    private IVehicle Vehicle;
    private double CurrentWeight;
    private IDriver Driver;

    public Delivery(Integer id, IVehicle vehicle, double currentWeight, IDriver driver, IPosition position, IItemPacked[] itemsPacked, IItem item) {
        Id = id;
        Vehicle = vehicle;
        CurrentWeight = currentWeight;
        Driver = driver;
        Position = position;
        ItemsPacked = itemsPacked;
        Item = item;
    }

    private IPosition Position;
    private IItemPacked[] ItemsPacked;
    private IItem Item;

    @Override
    public String getId() {
        return Integer.toString(this.Id);
    }

    @Override
    public void setVehicle(IVehicle var1, IDriver var2) throws DeliveryException {
        this.Vehicle = var1;
        this.Driver = var2;
    }

    @Override
    public IVehicle getVehicle() {
        return this.Vehicle;
    }

    @Override
    public boolean load(IItem var1, IPosition var2) throws DeliveryException {
        Position = var2;
        Item = var1;
        return false;
    }

    @Override
    public boolean unload(IItem var1, ItemStatus var2) throws DeliveryException {
        return false;
    }

    @Override
    public boolean unload(IDestination var1, ItemStatus var2) throws DeliveryException {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public IItem[] getRemainingItems() {
        return new IItem[0];
    }

    @Override
    public IDestination[] getRemainingDestinations() {
        return new IDestination[0];
    }

    @Override
    public void start() throws DeliveryException {

    }

    @Override
    public void end() throws DeliveryException {

    }

    @Override
    public double getCurrentWeight() {
        return 0;
    }

    @Override
    public void export(String var1) throws IOException {

    }
}
