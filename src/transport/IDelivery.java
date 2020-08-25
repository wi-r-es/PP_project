package transport;

import exceptions.DeliveryException;
import hr.IDestination;
import hr.IDriver;

public interface IDelivery extends IExporter {
    String getId();

    void setVehicle(IVehicle var1, IDriver var2) throws DeliveryException;

    IVehicle getVehicle();

    boolean load(IItem var1, IPosition var2) throws DeliveryException;

    boolean unload(IItem var1, ItemStatus var2) throws DeliveryException;

    boolean unload(IDestination var1, ItemStatus var2) throws DeliveryException;

    boolean isEmpty();

    IItem[] getRemainingItems();

    IDestination[] getRemainingDestinations();

    void start() throws DeliveryException;

    void end() throws DeliveryException;

    double getCurrentWeight();
}

