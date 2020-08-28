package transport;

import exceptions.PositionException;

public class Position implements IPosition {
    private int X, Y, Z;

    @Override
    public int getX() {
        return X;
    }

    @Override
    public void setX(int x) {
        X = x;
    }

    @Override
    public int getY() {
        return Y;
    }

    @Override
    public void setY(int y) {
        Y = y;
    }

    @Override
    public int getZ() {
        return Z;
    }

    @Override
    public void setZ(int z) {
        Z = z;
    }
}
