package transport;

import exceptions.DeliveryException;
import hr.IDestination;
import hr.IDriver;
import hr.LicenseType;

import java.io.IOException;
import java.util.Arrays;

public class Delivery implements IDelivery {

    private final Integer Id;
    private IVehicle Vehicle;
    private double CurrentWeight;
    private IDriver Driver;
    private IPosition Position;
    private IItem[] ItemsPacked;
    private int insidePosition = 0;

    private static Integer IDCOUNT=0;
    private final static int MAXITEMSDELIVERY = 50;



    public Delivery() {
        Id = ++IDCOUNT;
        Vehicle = null;
        CurrentWeight = 0;
        Driver = null;
        Position = null;
        ItemsPacked = new ItemPacked[MAXITEMSDELIVERY];

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
                            if ( var2.haveLicense(tempV[i]) ){
                                allLicensesCheck = true;  //esta bem pensado ?
                            }else allLicensesCheck = false;
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
                } else if (Arrays.deepEquals(this.getVehicle().getTransportationTypes(), var1.getTransportationTypes())) { //does this work like i think it does??
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
                        } } catch(ArrayIndexOutOfBoundsException e){
                                System.err.println("Index out of Bounds");
                            }
                    } // Delivery is Empty || Objects don't Collide
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
            if (confirmDelete > ItemsPacked.length){
                return true;
            } else return false;
        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds...");
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        if( insidePosition == 0){
            return true;
        } return false;
    }

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
            for (int i = 1; i < DestinationTemp.length; i++) {    // tou a fazer este loop para apagar os elementos repetidos bem ?
                if (current.equals(DestinationTemp[i]) && !found) { //este equals chega?
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

    @Override //falta este
    public void export(String var1) throws IOException {
        //Serialize an object to a specific format that can be stored.
    }





}
