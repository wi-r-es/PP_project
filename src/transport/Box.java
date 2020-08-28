package transport;

public class Box implements IBox{

    private int Depth;
    private int Height;
    private int Length;
    private int Volume;

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
}
