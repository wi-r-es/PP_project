package transport;

import hr.ICustomer;
import hr.IDestination;

public class ItemPacked extends Item implements IItemPacked{
//class for when item is set for delivery
    private IItem Item;
    private IPosition Position;

    public ItemPacked(String reference, String description, TransportationTypes[] transportationTypes, ICustomer customer, IDestination destination, int depth, int height, int length, int volume, double weight, IItem item, IPosition position) {
        super(reference, description, transportationTypes, customer, destination, depth, height, length, volume, weight);
        Item = item;
        Position = position;
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
