package com.example.ships;

import com.example.Coordinates;

public abstract class Ship {
    private Coordinates[] coords;

    public Ship(Coordinates[] coords) {
        this.coords = coords;
    }

    public Coordinates[] getCoords() {
        return coords;
    }

    public abstract int getSize();
}
