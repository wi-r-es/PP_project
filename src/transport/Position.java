/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package transport;


import exceptions.PositionException;

public class Position implements IPosition {
    private int X, Y, Z;

    @Override
    public int getX() {
        return X;
    }

    @Override
    public void setX(int x) throws PositionException {
        if( x<0) {throw new PositionException(" X Coordinate is negative...");}
        else X = x;

    }

    @Override
    public int getY() {
        return Y;
    }

    @Override
    public void setY(int y) throws PositionException {
        if( y<0) {throw new PositionException(" Y Coordinate is negative...");}
        else Y = y;
    }

    @Override
    public int getZ() {
        return Z;
    }

    @Override
    public void setZ(int z) throws PositionException {
        if( z<0) {throw new PositionException(" Z Coordinate is negative...");}
        else Z = z;
    }


}
