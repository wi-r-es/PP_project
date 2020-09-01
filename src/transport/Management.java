package transport;

import exceptions.DeliveryException;
import exceptions.ManagementException;
import hr.ICustomer;
import hr.IDestination;
import hr.IDriver;

import java.util.ArrayList;
import java.util.List;

public class Management implements IManagement {


    private List<IItem> Items = new ArrayList<IItem>();
    private List<IVehicle> Vehicles = new ArrayList<IVehicle>();
    private List<IDriver> Drivers = new ArrayList<IDriver>();
    private List<IDelivery> Deliveries = new ArrayList<IDelivery>();



    private final static int MAXITEMS = 50;
    private final static int MAXVEHICLES = 20;
    private final static int MAXDRIVERS = 20;

    // depois preciso que me ajudes a dar os throws as exceptions
    @Override
    public boolean addItem(IItem var1) throws ManagementException {

        if (Items.size() >= MAXITEMS)
            return false;
        else {
            Items.add(var1);
            return true;
        }
    }

    @Override
    public boolean removeItem(IItem var1) throws ManagementException {

        return Items.remove(var1);
    }

    @Override
    public IItem[] getItems() {

        IItem[] ItemsArray = new IItem[ Items.size() ];
        Items.toArray( ItemsArray );
        /**/
        return ItemsArray;
    }

    @Override
    public IItem[] getItems(ICustomer var1) {

        List<IItem> Items_Customer = new ArrayList<IItem>();

        for( IItem temp : Items){
            if( (temp.getCustomer()).equals(var1) ){
                Items_Customer.add(temp);
            }
        }
        IItem[] ItemsList = new IItem[ Items_Customer.size() ];
        Items_Customer.toArray( ItemsList);
        return ItemsList;
    }

    @Override
    public IItem[] getItems(IDestination var1) {

        List<IItem> Items_Destination = new ArrayList<IItem>();

        for( IItem temp : Items){
            if( (temp.getDestination()).equals(var1) ){
                Items_Destination.add(temp);
            }
        }
        IItem[] ItemsList = new IItem[ Items_Destination.size() ];
        Items_Destination.toArray( ItemsList);
        return ItemsList;

    }

    @Override
    public IItem[] getItems(TransportationTypes var1) {

        List<IItem> Items_TransportationType = new ArrayList<IItem>();

        for( IItem tempItem : Items){
            for ( TransportationTypes tempTransportationType : tempItem.getTransportationTypes()) {
                if( (tempTransportationType.equals(var1) )){
                    Items_TransportationType.add(tempItem);
                }
            }

    }   IItem[] ItemsList = new IItem[Items_TransportationType.size()];
        Items_TransportationType.toArray( ItemsList);
        return ItemsList;
    }

    @Override
    public IItem[] getItems(ItemStatus var1) {

        List<IItem> Items_Status = new ArrayList<IItem>();

        for( IItem temp : Items){
            if( (temp.getStatus()).equals(var1) ){
                Items_Status.add(temp);
            }
        }
        IItem[] ItemsList = new IItem[Items_Status.size()];
        Items_Status.toArray( ItemsList);
        return ItemsList;
    }

    @Override
    public boolean addVehicle(IVehicle var1) throws ManagementException {

        if (Vehicles.size() >= MAXVEHICLES)
            return false;
        else {
            Vehicles.add(var1);
            return true;
        }
    }

    @Override
    public boolean addDriver(IDriver var1) throws ManagementException {

        if (Drivers.size() >= MAXDRIVERS)
            throw new ManagementException("Vehicle Container Full");

        else {
            Drivers.add(var1);
            return true;
        }
    }

    @Override
    public boolean removeDriver(IDriver var1) throws ManagementException {
        return Drivers.remove(var1);
    }

    @Override
    public boolean removeVehicle(IVehicle var1) throws ManagementException {
        return Vehicles.remove(var1);
    }

    @Override
    public IVehicle[] getFleet() {

        IVehicle[] VehiclesArray = new IVehicle[ Vehicles.size()];
        Vehicles.toArray(VehiclesArray);
        return VehiclesArray;
    }

    @Override
    public IVehicle[] getFleet(VehicleStatus var1) {
        List<IVehicle> Vehicle_Status = new ArrayList<IVehicle>();

        for( IVehicle temp : Vehicles){
            if( (temp.getStatus()).equals(var1) ){
                Vehicle_Status.add(temp);
            }
        }
        IVehicle[] VehiclesList = new IVehicle[ Vehicle_Status.size() ];
        Vehicle_Status.toArray( VehiclesList);
        return VehiclesList;
    }

    @Override
    public IVehicle[] getFleet(TransportationTypes var1) {
        List<IVehicle> Vehicle_Status = new ArrayList<IVehicle>();

        for( IVehicle tempVehicle : Vehicles){
            for( TransportationTypes tempTransportationType : tempVehicle.getTransportationTypes()){
                if( (tempTransportationType.equals(var1)) ) {
                    Vehicle_Status.add(tempVehicle);
                }
            }
        }
        IVehicle[] VehicleList = new IVehicle[Vehicle_Status.size()];
            Vehicle_Status.toArray( VehicleList);
            return VehicleList;

    }


    @Override
    public IVehicle[] getFleet(VehicleStatus var1, TransportationTypes var2) {
        List<IVehicle> Vehicle_ST = new ArrayList<IVehicle>();

        for( IVehicle tempVehicleSTT : Vehicles){
            if( (tempVehicleSTT.getStatus()).equals(var1) ){
                for( TransportationTypes tempTransportationType : tempVehicleSTT.getTransportationTypes()){
                    if( (tempTransportationType.equals(var1)) ) {
                        Vehicle_ST.add(tempVehicleSTT);
                    }

                }
            }
        }
        IVehicle[] VehicleList = new IVehicle[Vehicle_ST.size()];
        Vehicle_ST.toArray( VehicleList);
        return VehicleList;
    }

    @Override
    public boolean addDelivery(IDelivery var1) throws ManagementException {
        Deliveries.add(var1);
        return true;

    }

    @Override
    public void deliveredItem(String var1, String var2) throws Exception { /* var1 ID Delivery var2 item reference */

        try {
            for (IDelivery DeliveryTemp : Deliveries) {
                if (var1.equals(DeliveryTemp.getId())) {
                    for (IItem ItemTemp : Items) {
                        if (var2.equals(ItemTemp.getReference())) {
                            ItemTemp.setStatus( ItemStatus.DELIVERED ); //estou a mudar o valor na lista ?? Sim, estás, está bem feito!
                        }
                    }

                }
            }
        }
        catch (Exception e){
            System.out.println("Error trying to access Item/Delivery"); //nao percebi bem os throws desta funcao
                                                                        //Eles pedem Exception aqui? se pedem é isto que tens diria eu
        }

    }

    @Override
    public void deliveredItem(String var1, IDestination var2) throws Exception {

        try {
            for (IDelivery DeliveryTemp : Deliveries) {
                if (var1.equals(DeliveryTemp.getId())) {
                    for (IItem ItemTemp : DeliveryTemp.getRemainingItems()) {
                        if (var2.equals(ItemTemp.getDestination())) { //nao sei como aceder aos items dentro das deliveries, DONE! alguma duvida diz
                            ItemTemp.setStatus(ItemStatus.DELIVERED);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println("Error trying to access Item/Delivery"); //nao percebi bem os throws desta funcao
        }

    }

    @Override
    public ItemStatus checkItemStatus(String var1) throws Exception {

        try{
            for( IItem temp : Items){
                if( (temp.getReference()).equals(var1) ) return temp.getStatus();
            }
        }
        catch(Exception e){
            System.out.println("Item Couldn't be Found...");
        }
        return null;
    }

    @Override
    public void startDelivery(String var1) throws DeliveryException {
        List<IItem> Items_Delivery = new ArrayList<IItem>();
        for( IDelivery tempD : Deliveries ){
            if( var1.equals(tempD.getId())){
                tempD.start();
            }
        }
    }

    @Override
    public void stopDelivery(String var1) throws DeliveryException {
        List<IItem> Items_Delivery = new ArrayList<IItem>();
        for( IDelivery tempD : Deliveries ){
            if( var1.equals(tempD.getId())){
                tempD.end();
            }
        }
    }
}
