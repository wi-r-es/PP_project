/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package transport;


import exceptions.DeliveryException;
import exceptions.ManagementException;
import hr.ICustomer;
import hr.IDestination;
import hr.IDriver;

public interface IManagement {
    boolean addItem(IItem var1) throws ManagementException;

    boolean removeItem(IItem var1) throws ManagementException;

    IItem[] getItems();

    IItem[] getItems(ICustomer var1);

    IItem[] getItems(IDestination var1);

    IItem[] getItems(TransportationTypes var1);

    IItem[] getItems(ItemStatus var1);

    boolean addVehicle(IVehicle var1) throws ManagementException;

    boolean addDriver(IDriver var1) throws ManagementException;

    boolean removeDriver(IDriver var1) throws ManagementException;

    boolean removeVehicle(IVehicle var1) throws ManagementException;

    IVehicle[] getFleet();

    IVehicle[] getFleet(VehicleStatus var1);

    IVehicle[] getFleet(TransportationTypes var1);

    IVehicle[] getFleet(VehicleStatus var1, TransportationTypes var2);

    boolean addDelivery(IDelivery var1) throws ManagementException;

    void deliveredItem(String var1, String var2) throws Exception;

    void deliveredItem(String var1, IDestination var2) throws Exception;

    ItemStatus checkItemStatus(String var1) throws Exception;

    void startDelivery(String var1) throws DeliveryException;

    void stopDelivery(String var1) throws DeliveryException;
}

