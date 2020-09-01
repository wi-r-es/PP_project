package transport;

import hr.ICustomer;
import hr.IDestination;

public class Item implements IItem {

    private final String Reference;
    private String Description;
    private TransportationTypes[] TransportationType;
    private ICustomer Customer;
    private IDestination Destination;
    private ItemStatus ItemStatus;
    private  int Depth;
    private  int Height;
    private final int Length;
    private final int Volume;
    private final double Weight;

    /* Constructor*/

    public Item(String reference, String description, transport.TransportationTypes[] transportationTypes, ICustomer customer, IDestination destination, transport.ItemStatus itemStatus, int depth, int height, int length, int volume, double weight) {
        Reference = reference;
        Description = description;
        TransportationType = transportationTypes;
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

        return TransportationType;
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
