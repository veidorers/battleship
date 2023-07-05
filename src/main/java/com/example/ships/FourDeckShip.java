package com.example.ships;

import com.example.Coordinates;

public class FourDeckShip extends Ship {
    private static final int SIZE = 4;

    public FourDeckShip(Coordinates[] coords) {
        super(coords);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
