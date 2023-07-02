package com.example;

import com.example.ships.SingleDeckShip;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Field {
    private List<SingleDeckShip> ships;
    private FieldUnits field[][];

    public Field() {
        this.ships = new ArrayList<>();
        field = new FieldUnits[10][10];

        for(int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = FieldUnits.EMPTY;
            }
        }
    }

    public void printField() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }

    public void addShip(SingleDeckShip ship) {
        ships.add(ship);
        Coordinates coords = ship.getCoords();

        field[coords.x][coords.y] = FieldUnits.SHIP;
        if(coords.x != 0) {
            field[coords.x - 1][coords.y - 1] = FieldUnits.AUREOLE;
            field[coords.x - 1][coords.y] = FieldUnits.AUREOLE;
            field[coords.x - 1][coords.y + 1] = FieldUnits.AUREOLE;
        }
        if(coords.x != 9) {
            field[coords.x + 1][coords.y - 1] = FieldUnits.AUREOLE;
            field[coords.x + 1][coords.y] = FieldUnits.AUREOLE;
            field[coords.x + 1][coords.y + 1] = FieldUnits.AUREOLE;
        }
        if(coords.y != 0) {
            field[coords.x - 1][coords.y - 1] = FieldUnits.AUREOLE;
            field[coords.x][coords.y - 1] = FieldUnits.AUREOLE;
            field[coords.x + 1][coords.y - 1] = FieldUnits.AUREOLE;
        }
        if(coords.y != 9) {
            field[coords.x - 1][coords.y + 1] = FieldUnits.AUREOLE;
            field[coords.x][coords.y + 1] = FieldUnits.AUREOLE;
            field[coords.x + 1][coords.y + 1] = FieldUnits.AUREOLE;
        }
    }

    public void placeShips() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        for (int i = 0; i < 3; i++) {
            Coordinates coords = new Coordinates();

            while(!validInput) {
                System.out.println("Введи координаты " + (i + 1) + " однопалубного коробля (формат: x,y)");
                String line = scanner.nextLine();

                if(line.length() == 3 && line.charAt(1) == ',') {
                    String[] stringNumbers = line.split(",");
                    try {
                        coords.x = Integer.parseInt(stringNumbers[0]);
                        coords.y = Integer.parseInt(stringNumbers[1]);

                        if(field[coords.x][coords.y] == FieldUnits.AUREOLE)
                            System.out.println("Корабли должны располагаться друг от друга на расстоянии 1 клетки!");
                        else
                            validInput = true;
                    } catch(NumberFormatException e) {
                        System.out.println("Координаты неверные. X и Y должны быть числами!");
                    }
                } else {
                    System.out.println("Координаты неверные. Правильный формат: x,y");
                }
            }

            validInput = false;
            addShip(new SingleDeckShip(coords));
        }
    }
}
