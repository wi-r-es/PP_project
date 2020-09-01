package transport;

import exceptions.DeliveryException;
import exceptions.ManagementException;
import hr.ICustomer;
import hr.IDestination;
import hr.IDriver;
import org.json.simple.ItemList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Management implements IManagement {


    private List<IItem> Items = new ArrayList<IItem>();
    private List<IVehicle> Vehicles = new ArrayList<IVehicle>();
    private List<IDriver> Drivers = new ArrayList<IDriver>();
    private List<IDelivery> Deliveries = new ArrayList<IDelivery>();

    private IItem[] ItemsList = new IItem[MAXITEMS];
    private IVehicle[] VehiclesList = new IVehicle[MAXVEHICLES];
    private IDriver[] DriversList = new IDriver[MAXDRIVERS];
    private IDelivery[] DeliveriesList = new IDelivery[MAXDELIVERIES];


    private final static int MAXITEMS = 50;
    private final static int MAXVEHICLES = 20;
    private final static int MAXDRIVERS = 20;
    private final static int MAXDELIVERIES = 100;

    // Methods
    @Override
    public boolean addItem(IItem var1) throws ManagementException {

        if( var1.getStatus().equals(ItemStatus.NON_DELIVERED)){
             if ( var1.getTransportationTypes().equals(null) ){ // aqui tbm diz que da sempre false... nao sei como fazer isto....
                 throw new ManagementException("Item haven't specified a Transportation Type...");
             } else {
                 if(ItemsList[0] == null){
                     ItemsList[0] = var1;
                 } else {
                     try {
                         for( int i =1; i< ItemsList.length ; i++){

                             if(ItemsList[i] == null){
                                 if( ItemsList[i-1] != null  && ItemsList[i+1] == null ){
                                     ItemsList[i] = var1;
                                     return true;
                                 } else {Arrays.sort(ItemsList, i-1 , ItemsList.length); i--;}
                             }
                         }
                     } catch (IndexOutOfBoundsException e){
                         System.out.println("Index out of bounds...");
                         return false;
                     }
                 }
             }
        } else {
            throw new ManagementException("Item Status isn't NON_DELIVERED");

        }
        return false;
    }


    @Override
    public boolean removeItem(IItem var1) throws ManagementException {

        if(ItemsList[0] == null){
            Arrays.sort(ItemsList);
            if (ItemsList[0] == null){
                System.out.println("No Item to be removed..."); //double-check if ItemsList is empty
                return false;
            }
        } else {
            try {
                for( int i =0; i < MAXITEMS ; i++){

                    if(ItemsList[i].equals(var1)){
                        if( ItemsList[i].getReference().equals(var1.getReference()) && ItemsList[i].getCustomer().equals(var1.getCustomer()) ){ //deep check the condition validated before
                            ItemsList[i]=null;
                            Arrays.sort(ItemsList, i, ItemsList.length);
                            return true;
                        } else return false;
                    } else return false;
                }
            } catch (IndexOutOfBoundsException){
                System.out.println("Index out of bounds...");
                return false;
            }
        } return false;
    }


    @Override
    public IItem[] getItems() {

        if(ItemsList[0] == null){
            Arrays.sort(ItemsList);
            if (ItemsList[0] == null){
                System.out.println("No Items to be found..."); //double-check if ItemsList is empty
            }
        } else {
            try {
                int counter= 0;
                for( int i =0; i< ItemsList.length ; i++){
                    if ( ItemsList[i] == null){ counter = i; }
                    else if( i ==  MAXITEMS-1 ) { counter = i; }
                }
                IItem[] ItemsCopy = Arrays.copyOfRange(ItemsList, 0, counter);
                return ItemsCopy;
            } catch (IndexOutOfBoundsException e){
                System.out.println("Index out of bounds...");
            }
        }
        //dou return de um array vazio ?
    }

    @Override
    public IItem[] getItems(ICustomer var1) {

        if(ItemsList[0] == null){
            Arrays.sort(ItemsList);
            if (ItemsList[0] == null){
                System.out.println("No Items to be found..."); //double-check if ItemsList is empty
            }
        } else {
            try {
                int numOfItems = 0;
                int[] ItemsIndexes = new int[25];
                int counter= 0;
                for( int i =0; i< ItemsList.length ; i++){
                    if ( ItemsList[i].getCustomer().equals(var1) ){
                        ItemsIndexes[numOfItems]=i;
                        numOfItems++;
                    }
                }
                IItem[] ItemsCopy = new IItem[numOfItems];
                for (int i =0; i<= numOfItems; i++) { //verificar se este for loop esta bem confecionado xD
                    ItemsCopy[i] = ItemsList[ItemsIndexes[i]];
                }
                return ItemsCopy;
            } catch (IndexOutOfBoundsException e){
                System.out.println("Index out of bounds...");
            }
        }
        //dou return de um array vazio ?
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
