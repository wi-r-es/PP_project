package transport;


public class ItemPacked extends Item implements IItemPacked{
//class for when item is set for delivery
    private IItem Item;
    private IPosition Position;


    public ItemPacked(IItem item, IPosition position) { //esta bem feito ?
        super(item);
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
