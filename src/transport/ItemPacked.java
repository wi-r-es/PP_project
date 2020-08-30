package transport;

import hr.ICustomer;
import hr.IDestination;

public class ItemPacked extends Item implements IItemPacked{

    private IItem Item;
    private IPosition Position;

    public ItemPacked(String reference, String description, transport.TransportationTypes[] transportationTypes, ICustomer customer, IDestination destination, transport.ItemStatus itemStatus, int depth, int height, int length, int volume, double weight) {
        super(reference, description, transportationTypes, customer, destination, itemStatus, depth, height, length, volume, weight);
    }

    @Override
    public IItem getItem() {
        return Item;
    }

    @Override
    public IPosition getPosition() {
        return Position;
    }

    @Override
    public void setPosition(IPosition var1) {

        Position = var1;
    }
}
