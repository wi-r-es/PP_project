/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package transport;


import exceptions.PositionException;

public interface IPosition {
    int getX();

    int getY();

    int getZ();

    void setX(int var1) throws PositionException;

    void setY(int var1) throws PositionException;

    void setZ(int var1) throws PositionException;


}

