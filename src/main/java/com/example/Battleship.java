package com.example;

public class Battleship {
    public static void main(String[] args) {
        Field field = new Field();
        field.placeShips();

        System.out.println("*****************");

        field.printField();
    }
}
