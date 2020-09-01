package transport;

import exceptions.DeliveryException;
import hr.IDestination;
import hr.IDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Delivery implements IDelivery {

    private Integer Id;
    private IVehicle Vehicle;
    private double CurrentWeight;
    private IDriver Driver;
    private IPosition Position;
    private List<IItem> ItemsPacked = new ArrayList<IItem>();
    private int xPosition;
    private int yPosition;
    private int zPosition;

    // private static int MAX_ITEMS_PER_CARGO = 10;


    public Delivery(Integer id, IVehicle vehicle, double currentWeight, IDriver driver, IPosition position, List<IItem> itemsPacked) {
        Id = id;
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
        this.Vehicle = var1;
        this.Driver = var2;
    }

    @Override
    public IVehicle getVehicle() {
        return this.Vehicle;
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

    @Override
    public boolean load(IItem var1, IPosition var2) throws DeliveryException {
        if (isEmpty()) {
            this.Position = var2;
            this.Position.setPStatus(PositionAvailability.OCCUPIED);
            this.ItemsPacked.add(var1);
            this.CurrentWeight += var1.getWeight();

            //inicio do arrumamento e ocupacao do espaco mais realista...
            setxPosition(var1.getHeight()); //why da fuck 'e que isto da erro ? Dava erro prk tu tinhas isto depois do return true, ou seja nunca na vida chegava a este codigo, logo o compilador avisa-te e dá erro
            setyPosition(var1.getLength());
            setzPosition(var1.getDepth());

            return true;

        } else {

            if (isPositionFree(var2)) {
                int counter = 0;
                // no javadoc diz Load/add a new item to the delivery in a given position considering transportation restrictions.
                for (int i = 0; i < ItemsPacked.size(); i++) {
                    if (Arrays.equals((ItemsPacked.get(i).getTransportationTypes()), var1.getTransportationTypes())) {
                        counter++;
                    }
                }
                if (counter == ItemsPacked.size()) { //check if all the existing items are in the same transportation type category
                    this.Position = var2;
                    this.Position.setPStatus(PositionAvailability.OCCUPIED);
                    this.ItemsPacked.add(var1);
                    this.CurrentWeight += var1.getWeight();
                    return true;
                } else {
                    throw new DeliveryException("Transportation Types don't match...");
                }
            } else{
                throw new DeliveryException("Given Position Is Occupied...");
            }
        }
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
        return false; //sem esta linha aparece como erro for some reason...
                        //Claro, imagina pode-se dar o caso de entrar no primeiro else mas nunca entrar no if, caso isso aconteça nao passa por nenhum return... por isso é que te dá erro
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
        return ItemsPacked.isEmpty();
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
}
