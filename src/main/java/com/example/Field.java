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

        for (int i = 0; i < 10; ++i) {
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

    public void placeShips() {
        placeShips(SingleDeckShip.class);
    }

    public void addShip(SingleDeckShip ship) {
        ships.add(ship);
        Coordinates coords = ship.getCoords();

        field[coords.y][coords.x] = FieldUnits.SHIP;

        int xMin = Math.max(coords.x - 1, 0);
        int xMax = Math.min(coords.x + 1, 9);

        int yMin = Math.max(coords.y - 1, 0);
        int yMax = Math.min(coords.y + 1, 9);

        for(int i = xMin; i <= xMax; ++i) {
            for(int j = yMin; j <= yMax; ++j) {
                if(field[j][i] == FieldUnits.SHIP)
                    continue;

                field[j][i] = FieldUnits.AUREOLE;
            }
        }
    }


    public void placeShips(Class<SingleDeckShip> clazz) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        for (int i = 0; i < 2; i++) {
            Coordinates coords = new Coordinates();

            while (!validInput) {
                System.out.println("Введи координаты " + (i + 1) + " однопалубного коробля (формат: x,y)");
                String line = scanner.nextLine();

                if (line.length() == 3 && line.charAt(1) == ',') {
                    String[] stringNumbers = line.split(",");
                    try {
                        coords.x = Integer.parseInt(stringNumbers[0]);
                        coords.y = Integer.parseInt(stringNumbers[1]);
                        if (field[coords.x][coords.y] == FieldUnits.AUREOLE)
                            System.out.println("Корабли должны располагаться друг от друга на расстоянии 1 клетки!");
                        else
                            validInput = true;

                    } catch (NumberFormatException e) {
                        System.out.println("Координаты неверные. X и Y должны быть числами!");
                    }
                } else {
                    System.out.println("Координаты неверные. Правильный формат: x,y");
                }
            }

            validInput = false;
            addShip(new SingleDeckShip(coords));

            printField();
        }
    }

}
