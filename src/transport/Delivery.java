package transport;

import exceptions.DeliveryException;
import hr.IDestination;
import hr.IDriver;
import hr.LicenseType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Delivery implements IDelivery {

    private final Integer Id;
    private IVehicle Vehicle;
    private double CurrentWeight;
    private IDriver Driver;
    private IPosition Position;
    private IItem[] ItemsPacked;
    private int zPosition;
    private int yPosition;
    private int xPosition;

    // private static int MAX_ITEMS_PER_CARGO = 10;
    private static Integer IDCOUNT=0;
    private static double MAX_VOLUME_PER_BOX=1000; //cm3
    private static int MAXITEMSDELIVERY = 50;
    /* private static int XMAX= 260; //cm
    private static int YMAX= */


    public Delivery(Integer id) {
        Id = ++IDCOUNT;
        Vehicle = null;
        CurrentWeight = 0;
        Driver = null;
        Position = null;
        ItemsPacked = new IItem[MAXITEMSDELIVERY];

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
                    boolean allLicensesCheck = false;
                    try{
                        for (int i=0; i< tempV.length ; i++){
                            if ( var2.haveLicense(tempV[i]) ){
                                allLicensesCheck = true;
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
        if ( var1.getStatus().equals(ItemStatus.NON_DELIVERED ) ){
            if ( getVehicle().equals(null) || getDriver().equals(null) ){ // aparece me que da sempre false qd tenho um construtor que incializa tudo a null menos o ID como corrijo
                throw new DeliveryException("Vehicle/Driver not assigned...");
            } else if (getVehicle().getStatus().equals(VehicleStatus.IN_PREPARATION)){
                if (/* condicao de volume para saber se ha espaco para outro item */){
                    //if item is overlaping or outside(overflowing)
                    if( getCurrentWeight() >= getVehicle().getMaxWeight() ){
                        throw new DeliveryException("Weight exceeds Limits...");
                    } else if( Arrays.deepEquals(this.getVehicle().getTransportationTypes(), var1.getTransportationTypes())){ //does this work like i think it does??
                        // codigo de load...
                    } else throw new DeliveryException("Transportation Restrictions don't match...");
                } else throw new DeliveryException("Items Overflowing...");
            } else throw new DeliveryException("Vehicle given isn't IN_PREPARATION...");
        } else throw new DeliveryException("Item status isn't NON_DELIVERED...");
    }



    @Override
    public boolean unload(IItem var1, ItemStatus var2) throws DeliveryException {
        if( isEmpty() ) {
            throw new DeliveryException("Emptyness found...");

        }else{
            for (IItem itemTemp : ItemsPacked) {
                if ((itemTemp.getReference()).equals(var1.getReference())) {
                    ItemsPacked.remove(itemTemp);
                    var1.setStatus(var2);
                    this.Position.setPStatus(PositionAvailability.FREE);
                    subWeight(itemTemp);
                    return true;
                } else {
                    throw new DeliveryException("Item doesn't exist in Delivery...");
                }
            }
        }
        return false;
    }

    @Override
    public boolean unload(IDestination var1, ItemStatus var2) throws DeliveryException {
        if (isEmpty()) {
            throw new DeliveryException("Emptyness found...");
        } else{

            for (IItem itemTemp : ItemsPacked) {
                if ((itemTemp.getDestination()).equals(var1)) {
                    ItemsPacked.remove(itemTemp);
                    itemTemp.setStatus(var2);
                    this.Position.setPStatus(PositionAvailability.FREE);
                    subWeight(itemTemp);
                    return true;
                } else {
                    throw new DeliveryException("Items with giver destination don't exist in Delivery...");
                }
            }

        }
        return false;
    }

    @Override
    public boolean isEmpty() {

    }

    @Override
    public IItem[] getRemainingItems() {
        if (isEmpty()){
            System.err.println("There is no remaining items...");
            return null;
        } else {
            List<IItem> RemainingItems = new ArrayList<IItem>();
            for(IItem itemTemp : ItemsPacked){
                if( (itemTemp.getStatus()).equals(ItemStatus.NON_DELIVERED)){
                    RemainingItems.add(itemTemp);
                    /* like se cada package tiver 50x50x50
                    agora seria algo do tipo
                    xPosition -= itemTemp.getX
                    yPosition -= itemTemp.getY
                    zPosition -= itemTemp.getZ
                     */
                }
            }
            IItem[] RemaingList = new IItem[RemainingItems.size()];
            RemainingItems.toArray( RemaingList );
            return RemaingList;
        }
    }

    @Override
    public IDestination[] getRemainingDestinations() {
        if (isEmpty()){
            System.err.println("There is no remaining items with given destination ...");
            return null;
        } else {
            List<IDestination> RemainingDestinations = new ArrayList<IDestination>();
            for(IItem itemTemp : ItemsPacked){
                {
                    RemainingDestinations.add(itemTemp.getDestination());
                    /* like se cada package tiver 50x50x50
                    agora seria algo do tipo
                    xPosition -= itemTemp.getX
                    yPosition -= itemTemp.getY
                    zPosition -= itemTemp.getZ
                     */
                }
            }
            IDestination[] RemaingList = new IDestination[RemainingDestinations.size()];
            RemainingDestinations.toArray( RemaingList );
            return RemaingList;
        }
    } // same here

    @Override
    public void start() throws DeliveryException {

        if( !(this.Vehicle.getStatus().equals(VehicleStatus.IN_PREPARATION)) ){
            throw new DeliveryException("Vehicle Status isn't IN_PREPARATION...");
        }
        if( this.isEmpty() ){
            throw new DeliveryException("Emptiness Found...");
        }
        this.Vehicle.setStatus(VehicleStatus.IN_TRANSIT);
    }

    @Override
    public void end() throws DeliveryException {
        if( (this.Vehicle.getStatus().equals(VehicleStatus.IN_TRANSIT))) {
            this.Vehicle.setStatus(VehicleStatus.FREE);
            this.Driver.setStatus(DriverStatus.FREE);
            for( IItem itemsTemp : ItemsPacked ){
                if( itemsTemp.getStatus().equals(ItemStatus.NON_DELIVERED) ){
                    unload(itemsTemp, ItemStatus.ASSIGNED);
                }
            }
        }
    }

    @Override
    public double getCurrentWeight() {
        double total =0;
        for(IItem itemTemp : ItemsPacked ){
            total += itemTemp.getWeight();
        }
        return total;
    }

    @Override
    public void export(String var1) throws IOException {
        //Serialize an object to a specific format that can be stored.
    }


    public IDriver getDriver() {
        return Driver;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getzPosition() {
        return zPosition;
    }

    public void setzPosition(int zPosition) {
        this.zPosition = zPosition;
    }

    public boolean isPositionFree(IPosition var1) { // se sobrar tempo, tentar fazer com os height, length e depth para ficar mais correto
        // Exato, eu se fosse stor considerava bué se fizesses da forma correta, mas deixa para fim
        return (this.Position.getPStatus()).equals(PositionAvailability.FREE);

    }


    public void subWeight(IItem var1){
        this.CurrentWeight -= var1.getWeight();
    }

}
