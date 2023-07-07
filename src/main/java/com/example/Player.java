package com.example;

public class Player {
    public String name;
    public Field field;
    public Field enemyField;
    public int sunkShips;

    public Player(String name) {
        this.name = name;
        enemyField = new Field();
        sunkShips = 0;
    }
}
