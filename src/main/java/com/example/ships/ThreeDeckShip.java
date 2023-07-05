package com.example.ships;

import com.example.Coordinates;

public class ThreeDeckShip extends Ship {
    private static final int SIZE = 3;

    public ThreeDeckShip(Coordinates[] coords) {
        super(coords);
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
