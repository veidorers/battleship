package com.example;

public enum FieldUnits {
    EMPTY("\uD83D\uDFE6"), SHIP("\uD83D\uDFE2"), AUREOLE("\uD83D\uDFE8"), HIT("\uD83D\uDFE5");

    private String representation;

    private FieldUnits(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
