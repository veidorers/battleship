package com.example.ships;

import com.example.Coordinates;

public class DoubleDeckShip extends Ship {
    private final int SIZE = 2;

    public DoubleDeckShip(Coordinates[] coords) {
        super(coords);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
