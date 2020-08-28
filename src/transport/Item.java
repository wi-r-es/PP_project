package transport;

import hr.ICustomer;
import hr.IDestination;

public class Item implements IItem {

    private String Reference;
    private String Description;
    private TransportationTypes[] TransportationTypes;
    private ICustomer Customer;
    private IDestination Destination;
    private ItemStatus ItemStatus;
    private int Depth;
    private int Height;   /* nao deveria simplesmente dar extend a box e retirava todo o codigo que ja esta em BOX? os dados heigh length etc, e os respetivos set e get?? */
    private int Length;
    private int Volume;
    private double Weight;

    /* Constructor*/

    public Item(String reference, String description, transport.TransportationTypes[] transportationTypes, ICustomer customer, IDestination destination, transport.ItemStatus itemStatus, int depth, int height, int length, int volume, double weight) {
        Reference = reference;
        Description = description;
        TransportationTypes = transportationTypes;
        Customer = customer;
        Destination = destination;
        ItemStatus = itemStatus;
        Depth = depth;
        Height = height;
        Length = length;
        Volume = volume;
        Weight = weight;
    }

    @Override
    public String getReference() {
        return Reference;
    }

    @Override
    public String getDescription() { return Description; }

    @Override
    public void setDescription(String var1) { Description = var1; }

    @Override
    public TransportationTypes[] getTransportationTypes() {

        return TransportationTypes;
    }

    @Override
    public ICustomer getCustomer() {
        return Customer;
    }

    @Override
    public IDestination getDestination() {
        return Destination;
    }

    @Override
    public double getWeight() {
        return Weight;
    }

    @Override
    public ItemStatus getStatus() {
        return ItemStatus;
    }

    @Override
    public void setStatus(ItemStatus var1) { ItemStatus = var1;}

    @Override
    public int getDepth() {
        return Depth;
    }

    public void setDepth(int depth) {
        Depth = depth;
    }

    @Override
    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    @Override
    public int getLength() {
        return Length;
    }


    @Override
    public int getVolume() {
        return Volume;
    }


}
