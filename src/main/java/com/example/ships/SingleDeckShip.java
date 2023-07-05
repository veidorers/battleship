package com.example.ships;

import com.example.Coordinates;

public class SingleDeckShip extends Ship {
    private static final int SIZE = 1;

    public SingleDeckShip(Coordinates[] coords) {
        super(coords);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
