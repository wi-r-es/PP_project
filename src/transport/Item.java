
/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package transport;


import hr.ICustomer;
import hr.IDestination;
import org.json.simple.JSONObject;

public class Item implements IItem {

    private final String Reference;
    private String Description;
    private TransportationTypes[] transportationTypes;
    private ICustomer Customer;
    private IDestination Destination;
    private ItemStatus ItemStatus;
    private  int Depth;
    private  int Height;
    private final int Length;
    private final int Volume;
    private final double Weight;


    /* Constructor*/
    //construtor copia
    public Item (IItem item ){
        Reference = item.getReference();
        Description = item.getDescription();
        transportationTypes = item.getTransportationTypes();
        Customer = item.getCustomer();
        Destination = item.getDestination();
        ItemStatus = item.getStatus();
        Depth = item.getDepth();
        Height = item.getHeight();
        Length = item.getLength();
        Volume = item.getVolume();
        Weight = item.getWeight();
    }

    public Item(String reference, String description, transport.TransportationTypes[] transportationTypes, ICustomer customer, IDestination destination, int depth, int height, int length, int volume, double weight) {
        Reference = reference;
        Description = description;
        this.transportationTypes = transportationTypes;
        Customer = customer;
        Destination = destination;
        ItemStatus = transport.ItemStatus.NON_DELIVERED;
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

        return transportationTypes;
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


    @Override
    public int getHeight() {
        return Height;
    }


    @Override
    public int getLength() {
        return Length;
    }


    @Override
    public int getVolume() {
        return Volume;
    }

    @Override
    public JSONObject getObject(){
        JSONObject o1 = new JSONObject();
        o1.put("reference", this.Reference);
        o1.put("description" , this.Description);
        o1.put("customer" , this.getCustomer().getObject());
        o1.put("transportation type", this.transportationTypes);
        o1.put("destination", this.Destination.getObject());
        o1.put("depth", this.Depth);
        o1.put("height", this.Height);
        o1.put("length", this.Length);
        o1.put("volume", this.Volume);
        o1.put("weight", this.Weight);
        return o1;

    }



}
