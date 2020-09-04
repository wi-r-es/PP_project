/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */

package transport;


import exceptions.DeliveryException;
import hr.IDestination;
import hr.IDriver;
import hr.LicenseType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class Delivery implements IDelivery, Serializable {

    private final Integer Id;
    private IVehicle Vehicle;
    private double CurrentWeight;
    private IDriver Driver;
    private IPosition Position;
    private IItem[] ItemsPacked = new IItem[MAXITEMSDELIVERY];
    private int insidePosition = 0;

    private static Integer IDCOUNT=0;
    private final static int MAXITEMSDELIVERY = 50;



    public Delivery() {
        Id = ++IDCOUNT;
        Vehicle = null;
        CurrentWeight = 0;
        Driver = null;
        Position = null;

    }

    public Delivery(Integer id, IVehicle vehicle, double currentWeight, IDriver driver, IPosition position, IItem[] itemsPacked) {
        Id = ++IDCOUNT;
        Vehicle = vehicle;
        CurrentWeight = currentWeight;
        Driver = driver;
        Position = position;
        ItemsPacked = itemsPacked;
    }

    @Override
    public String getId() {
        return Integer.toString(this.Id);
    }

    @Override
    public void setVehicle(IVehicle var1, IDriver var2) throws DeliveryException {
        if( this.isEmpty() ){
            if( var1.getStatus().equals(VehicleStatus.FREE) ){
                if( var2.getStatus().equals(DriverStatus.FREE) ){
                    LicenseType[] tempV = new LicenseType[var1.getAllowedLicenses().length];
                    tempV = Arrays.copyOf(var1.getAllowedLicenses(), var1.getAllowedLicenses().length);
                    boolean allLicensesCheck = false;
                    try{
                        for (int i=0; i< tempV.length ; i++){
                            allLicensesCheck = var2.haveLicense(tempV[i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                            System.err.println("Index Out Of Bounds...");
                    }
                    if( allLicensesCheck ){
                        this.Vehicle=var1;
                        this.Driver=var2;
                        var1.setStatus(VehicleStatus.IN_PREPARATION);
                        var2.setStatus(DriverStatus.ASSIGNED);
                    } else throw new DeliveryException("Driver doesn't have required Licenses for specified vehicle...");
                } else throw new DeliveryException("Driver isn't free...");
            } else throw new DeliveryException("Vehicle isn't free...");
        } else throw new DeliveryException("An item was already assigned to the delivery...");
        throw new DeliveryException("Parameter is null");
    }

    @Override
    public IVehicle getVehicle() {
        return this.Vehicle;
    }

/**
 * Returns an boolean value whether the item is load or not
 * Throws DeliveryException - if: any parameter is null ;item status is not NON_DELIVERED ;if no vehicle and/or driver are assigned; the vehicle status is different from in preparation; if some item is outside (or is overflowing) the delivery or if some item is overlapping with other item; if weight is exceeded ;if transportation restrictions of the current are not valid for the item
 * @param  var1  an item object to be stored
 * @param  var2 the position where the items is set to be stored
 * @return true if item can be loaded, without object collision with other items; if it hasn't enough space, one more space will be created , returns false if item already exists in delivery
 *
 * **/
    @Override
    public boolean load(IItem var1, IPosition var2) throws DeliveryException {
        try{
            if (var1.getStatus().equals(ItemStatus.NON_DELIVERED)) {
            if (getVehicle()==null || getDriver()==null) {
                throw new DeliveryException("Vehicle/Driver not assigned...");
            } else if (getVehicle().getStatus().equals(VehicleStatus.IN_PREPARATION)) {
                updateCurrentWeight();
                if (getCurrentWeight() >= getVehicle().getMaxWeight()) {
                    throw new DeliveryException("Weight exceeds Limits...");
                } else if (Arrays.deepEquals(this.getVehicle().getTransportationTypes(), var1.getTransportationTypes())) {
                    if (!isEmpty()) {
                        try{
                            for (IItem tempItem : ItemsPacked) {
                                if (var1.getReference().equals(tempItem.getReference())) {
                                    return false;
                                }
                            }
                            for (int i = 0; i < insidePosition; i++) {   //canto superior
                                if ((((ItemPacked) ItemsPacked[i]).getPosition().getX() < var2.getX()) &&
                                    ((((ItemPacked) ItemsPacked[i]).getPosition().getX()) + ItemsPacked[i].getLength() > var2.getX()) &&
                                    (((ItemPacked) ItemsPacked[i]).getPosition().getY() < var2.getY()) &&
                                    ((((ItemPacked) ItemsPacked[i]).getPosition().getY()) + ItemsPacked[i].getHeight() > var2.getY()) &&
                                    (((ItemPacked) ItemsPacked[i]).getPosition().getZ() < var2.getZ()) &&
                                    ((((ItemPacked) ItemsPacked[i]).getPosition().getZ()) + ItemsPacked[i].getDepth() > var2.getZ()) &&
                                    //canto inferior
                                    (((ItemPacked) ItemsPacked[i]).getPosition().getX() < var2.getX() + var1.getLength()) &&
                                    ((((ItemPacked) ItemsPacked[i]).getPosition().getX()) + ItemsPacked[i].getLength() > var2.getX() + var1.getLength()) &&
                                    (((ItemPacked) ItemsPacked[i]).getPosition().getY() < var2.getY() + var1.getHeight()) &&
                                    ((((ItemPacked) ItemsPacked[i]).getPosition().getY()) + ItemsPacked[i].getHeight() > var2.getY() + var1.getHeight()) &&
                                    (((ItemPacked) ItemsPacked[i]).getPosition().getZ() < var2.getZ() + var1.getDepth()) &&
                                    ((((ItemPacked) ItemsPacked[i]).getPosition().getZ()) + ItemsPacked[i].getDepth() > var2.getZ() + var1.getDepth())
                                ) {
                                    throw new DeliveryException("Object Collision...");
                                }
                        }
                        } catch(ArrayIndexOutOfBoundsException e){
                                System.err.println("Index out of Bounds");
                            }
                    } // Delivery is Empty || Objects don't Collide
                    if ( insidePosition==ItemsPacked.length-1 ){
                        ItemsPacked = Arrays.copyOf(ItemsPacked, ItemsPacked.length+1);
                        ItemsPacked[ItemsPacked.length-1] = var1;
                        ItemsPacked[++insidePosition].setStatus(ItemStatus.ASSIGNED);
                        return true;
                    }
                    ItemsPacked[insidePosition] = new ItemPacked(var1, var2);
                    ItemsPacked[insidePosition++].setStatus(ItemStatus.ASSIGNED);
                    return true;

                } else throw new DeliveryException("Transportation Restrictions don't match...");
            } else throw new DeliveryException("Vehicle given isn't IN_PREPARATION...");
        } else throw new DeliveryException("Item status isn't NON_DELIVERED...");
        }
        catch(NullPointerException e){
            throw new DeliveryException(e.getMessage());
        }
    }


    /**
     * Returns an boolean value whether the item is load or not
     * Throws DeliveryException - if: any parameter is null ;item status is not NON_DELIVERED ;if no vehicle and/or driver are assigned; the vehicle status is different from in preparation; if some item is outside (or is overflowing) the delivery or if some item is overlapping with other item; if weight is exceeded ;if transportation restrictions of the current are not valid for the item
     * @param  var1  an item object to be stored
     * @param  var2 the position where the items is set to be stored
     * @return true if item can be loaded, without object collision with other items; if it hasn't enough space, one more space will be created , returns false if item already exists in delivery
     *
     * **/
    @Override
    public boolean unload(IItem var1, ItemStatus var2) throws DeliveryException {
        if( getDriver()==null || getVehicle()==null ) {
            throw new DeliveryException("No Vehicle/Driver Assigned...");
        }
        if ( !( var1.getStatus().equals(ItemStatus.DELIVERED) || var1.getStatus().equals(ItemStatus.NON_DELIVERED) ) ){
            throw new DeliveryException("parameter itemStatus is not ItemStatus.DELIVERED or ItemStatus.NON_DELIVERED");
        }
        if( !( getVehicle().getStatus().equals(VehicleStatus.IN_PREPARATION) && getVehicle().getStatus().equals(VehicleStatus.IN_TRANSIT) )){
            throw new DeliveryException("The vehicle status is different from in preparation or in transit");
        }
        try{
            for (int i = 0; i < ItemsPacked.length ; i++) {
                if(ItemsPacked[i].equals(var1)){
                    if( ItemsPacked[i].getReference().equals(var1.getReference()) && ItemsPacked[i].getCustomer().equals(var1.getCustomer()) ){ //deep check the condition validated before
                        ItemsPacked[i].setStatus(var2);
                        ItemsPacked[i]=null;
                        Arrays.sort(ItemsPacked, i, ItemsPacked.length);
                        insidePosition--;
                        updateCurrentWeight();
                        return true;
                    } else return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds...");
            return false;
        } return false;
    }

    /**
     * Returns an boolean value whether the item is unload or not
     * @param  var1  an destination of the items bo be unload
     * @param  var2 the status to assigned the items after they are successfully unload
     * @return true if item is unload , returns false if item doesn't exist in delivery
     *
     * **/
    @Override
    public boolean unload(IDestination var1, ItemStatus var2) throws DeliveryException {
        if( getDriver()==null || getVehicle()==null){
            throw new DeliveryException("No Vehicle/Driver Assigned...");
        }
        if ( !( var2.equals(ItemStatus.DELIVERED) || var2.equals(ItemStatus.NON_DELIVERED)) ){
            throw new DeliveryException("parameter itemStatus is not ItemStatus.DELIVERED or ItemStatus.NON_DELIVERED");
        }
        if( !( getVehicle().getStatus().equals(VehicleStatus.IN_PREPARATION) && getVehicle().getStatus().equals(VehicleStatus.IN_TRANSIT) )){
            throw new DeliveryException("The vehicle status is different from in preparation or in transit");
        }
        try{
            int confirmDelete = ItemsPacked.length;
            int numOfItems = 0;
            int[] ItemsIndexes = new int[25];
            int counter= 0;
            for (int i = 0; i < ItemsPacked.length ; i++) {
                if(ItemsPacked[i].getDestination().equals(var1)){
                    ItemsIndexes[numOfItems]=i;
                    numOfItems++;
                }
            }
            for (int i = 0; i < numOfItems ; i++) {
                ItemsPacked[ItemsIndexes[i]] = null;
                insidePosition--;
            }
            Arrays.sort(ItemsPacked);
            updateCurrentWeight();
            return confirmDelete > ItemsPacked.length;
        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds...");
            return false;
        }
    }
    /**
     * Returns an boolean value whether the delivery has items or not
     * @return true if delivery is empty , returns false delivery isn't empty
     *
     * **/
    @Override
    public boolean isEmpty() {
        return insidePosition == 0;
    }
    /**
     * Returns all the remaining items in delivery
     *
     * @return null in case there is no items remaining or an error has occurred; or return an array with the remaining items to be delivered
     *
     * **/
    @Override
    public IItem[] getRemainingItems() {
        if (isEmpty()) {
            return null;
        }else {
            try {
            IItem[] tempItem = new IItem[MAXITEMSDELIVERY];
            for (int i = 0; i < ItemsPacked.length; i++) {
                int j = 0;
                if (!(ItemsPacked[i].getStatus().equals(ItemStatus.DELIVERED))) {
                    tempItem[j++] = ItemsPacked[i];
                }
            }
            return tempItem; }
            catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Index out of bounds...");
            }
        } return null;
    }

    /**
     * Returns all the remaining destinations in delivery
     *
     * @return null in case there is no destinations remaining or an error has occurred; or return an array with the remaining destinations with items yet to be delivered
     *
     * **/
    @Override
    public IDestination[] getRemainingDestinations() {
        try{
            IDestination[] DestinationTemp = new IDestination[ItemsPacked.length];
            for( int i =0; i< ItemsPacked.length ; i++){
                DestinationTemp[i] = ItemsPacked[i].getDestination();
            }
            Arrays.sort(DestinationTemp);
            IDestination current = DestinationTemp[0];
            boolean found = false;
            for (int i = 1; i < DestinationTemp.length; i++) {
                if (current.equals(DestinationTemp[i]) && !found) {
                    DestinationTemp[i]= null;
                    found = true;
                } else if (current != DestinationTemp[i]) {
                    current = DestinationTemp[i];
                    found = false;
                }
            }
            Arrays.sort(DestinationTemp);
            return DestinationTemp;
        }catch(ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds...");
        }
        return null;
    }


    @Override
    public void start() throws DeliveryException {

        if (isEmpty()){
            throw new DeliveryException("Delivery is empty...");
        }
        if (this.getVehicle()==null || this.getDriver()==null ){
            throw new DeliveryException("No Vehicle/Driver assigned...");
        }
        if (this.getVehicle().getStatus().equals(VehicleStatus.IN_PREPARATION)){
            throw new DeliveryException("Vehicle Status not in preparation...");
        }
        this.Vehicle.setStatus(VehicleStatus.IN_TRANSIT);
    }

    @Override
    public void end() throws DeliveryException {
        if (this.getVehicle().getStatus().equals(VehicleStatus.IN_TRANSIT)){
            this.Vehicle.setStatus(VehicleStatus.FREE);
            this.Driver.setStatus(DriverStatus.FREE);
            try {
                for( IItem itemsTemp : ItemsPacked ){
                    if( itemsTemp.getStatus().equals(ItemStatus.NON_DELIVERED) ){
                        unload(itemsTemp, ItemStatus.ASSIGNED);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
            }
        } else throw new DeliveryException("Vehicle is not in transit...");
    }

    @Override
    public double getCurrentWeight() {
        return CurrentWeight;
    }
    public void updateCurrentWeight(){
        CurrentWeight=0;
        for(IItem itemTemp : ItemsPacked ){
            CurrentWeight += itemTemp.getWeight();
        }
    }

    public IDriver getDriver() {
        return Driver;
    }


    /**
     * Exports the data of a delivery to JSON
     * @param var1 path to where the file is going to be written
     * @return void
     *
     * **/
    @Override //For items
    public void export(String var1) throws IOException {
        try( FileWriter writer = new FileWriter(var1,true);) {
            //Serialize an object to a specific format that can be stored.
            writer.write(this.getObject().toJSONString());
            writer.write("["); writer.write("items");

            JSONArray.writeJSONString(Arrays.asList(this.getRemainingItems()), writer);
            writer.write("]" );


        }catch(ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds...");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
    public JSONObject getObject(){
        JSONObject o1 = new JSONObject();
        o1.put("delivery id", this.getId());
        o1.put("currentweight", (this.getCurrentWeight()));
        return o1;

    }
    /**
     * Exports the data of a delivery to JSON, based on transportation types
     * @param var1 path to where the file is going to be written
     * @return void
     *
     * **/
    public void exportTransportationTypes(String var1) throws IOException {
        try( FileWriter writer = new FileWriter("files/delivery.txt",true);) {
            //Serialize an object to a specific format that can be stored.
            writer.write(this.getObject().toJSONString());
            writer.write("["); writer.write("items");
            IItem[] tempItem = new IItem[MAXITEMSDELIVERY];
            int count =0;
            for (TransportationTypes tempTT : TransportationTypes.values() ){
                for (IItem temp : ItemsPacked){
                    TransportationTypes[] tempTTArr= temp.getTransportationTypes();
                    for (int i=0; i<tempTTArr.length ; i++){
                        if ( tempTTArr[i].equals(tempTT) ){
                            for (int j = 0; j < tempItem.length; j++) {
                                if (tempItem[j].equals(temp)){
                                    break;
                                }
                            }
                            tempItem[count++] = temp;
                        }
                    }
                }
            }
            JSONArray.writeJSONString(Arrays.asList(tempItem), writer);
            writer.write("]" );


        }catch(ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds...");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
