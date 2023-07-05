package com.example;

import com.example.ships.DoubleDeckShip;
import com.example.ships.Ship;
import com.example.ships.SingleDeckShip;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Field {
    private List<Ship> ships;
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
        System.out.println("  0️⃣1️⃣2️⃣3️⃣4️⃣5️⃣6️⃣7️⃣8️⃣9️⃣");

        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }


    public void placeShips() {
        placeDoubleDeckShips();
        placeSingleDeckShips();
    }


    public void addShip(Ship ship) {
        ships.add(ship);

        Coordinates[] coords = ship.getCoords();

        for(int i = 0; i < ship.getSize(); ++i)
            field[coords[i].y][coords[i].x] = FieldUnits.SHIP;

        int xMin = 9, xMax = 0, yMin = 9, yMax = 0;
        for(int i = 0; i < ship.getSize(); ++i) {
            if(coords[i].x > xMax) xMax = coords[i].x;
            if(coords[i].x < xMin) xMin = coords[i].x;

            if(coords[i].y > yMax) yMax = coords[i].y;
            if(coords[i].y < yMin) yMin = coords[i].y;
        }
        xMax = Math.min(xMax + 1, 9);
        xMin = Math.max(xMin - 1, 0);
        yMax = Math.min(yMax + 1, 9);
        yMin = Math.max(yMin - 1, 0);

        for(int i = xMin; i <= xMax; ++i) {
            for(int j = yMin; j <= yMax; ++j) {
                if(field[j][i] == FieldUnits.SHIP)
                    continue;

                field[j][i] = FieldUnits.AUREOLE;
            }
        }
    }


    public void placeSingleDeckShips() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        for (int i = 0; i < 4; i++) {
            Coordinates[] coords = new Coordinates[1];

            while (!validInput) {
                System.out.println("Введи координаты " + (i + 1) + " однопалубного коробля (формат: x,y)");
                String line = scanner.nextLine();

                if (line.length() == 3 && line.charAt(1) == ',') {
                    String[] stringNumbers = line.split(",");
                    try {
                        coords[0] = new Coordinates();

                        coords[0].x = Integer.parseInt(stringNumbers[0]);
                        coords[0].y = Integer.parseInt(stringNumbers[1]);
                        if (field[coords[0].y][coords[0].x] == FieldUnits.AUREOLE)
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

    public void placeDoubleDeckShips() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        for (int i = 0; i < 3; ++i) {
            Coordinates[] coords = new Coordinates[2];

            while (!validInput) {
                System.out.println("Введите координаты " + (i + 1) + " двухпалубного коробля (формат: x,y;x,y)");
                String line = scanner.nextLine();

                if (line.length() == 7 && line.charAt(1) == ',' && line.charAt(3) == ';' && line.charAt(5) == ',') {
                    String[] coordinates = line.split(";");
                    String[] coords1 = coordinates[0].split(",");
                    String[] coords2 = coordinates[1].split(",");

                    try {
                        for (int j = 0; j < coords.length; ++j) {
                            coords[j] = new Coordinates();
                        }

                        coords[0].x = Integer.parseInt(coords1[0]);
                        coords[0].y = Integer.parseInt(coords1[1]);
                        coords[1].x = Integer.parseInt(coords2[0]);
                        coords[1].y = Integer.parseInt(coords2[1]);

                        int xDiff = Math.abs(coords[0].x - coords[1].x);
                        int yDiff = Math.abs(coords[0].y - coords[1].y);

                        if(xDiff > 1 || yDiff > 1 || xDiff == yDiff) {
                            System.out.println("Корабль должен располагаться либо вертикально, либо горизонтально!");
                        }
                        else if (field[coords[0].y][coords[0].x] == FieldUnits.AUREOLE ||
                                field[coords[1].y][coords[1].x] == FieldUnits.AUREOLE) {
                            System.out.println("Корабли должны располагаться друг от друга на расстоянии 1 клетки!");
                        }
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
            addShip(new DoubleDeckShip(coords));

            printField();
        }
    }
}
