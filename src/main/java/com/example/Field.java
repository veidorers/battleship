package com.example;

import com.example.ships.*;

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

    public FieldUnits[][] getField() {
        return field;
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

    public Ship findShipByCoords(Coordinates coords) {
        for(Ship s : ships) {
            if(s.contains(coords))
                return s;
        }
        return null;
    }

    //clear the field from aureoles squares
    public void clearField() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; j++) {
                if(field[i][j] == FieldUnits.AUREOLE)
                    field[i][j] = FieldUnits.EMPTY;
            }
        }
    }

    public void placeShips() {
        placeFourDeckShips();
        placeThreeDeckShips();
        placeDoubleDeckShips();
        placeSingleDeckShips();

        clearField();
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

        for (int i = 0; i < 1; i++) {
            Coordinates[] coords = new Coordinates[1];

            while (!validInput) {
                System.out.println("Введи координаты " + (i + 1) + " однопалубного коробля (формат: x,y)");
                String line = scanner.nextLine();

                if (line.length() != 3 || line.charAt(1) != ',')
                    System.out.println("Координаты неверные. Правильный формат: x,y");

                else {
                    String[] stringNumbers = line.split(",");
                    try {
                        coords[0] = new Coordinates();

                        coords[0].x = Integer.parseInt(stringNumbers[0]);
                        coords[0].y = Integer.parseInt(stringNumbers[1]);
                        if (field[coords[0].y][coords[0].x] != FieldUnits.EMPTY)
                            throw new InvalidShipPlacementException("Корабли должны располагаться друг от друга на расстоянии 1 клетки!");

                        validInput = true;

                    } catch (NumberFormatException e) {
                        System.out.println("Координаты неверные. X и Y должны быть числами!");
                    } catch (InvalidShipPlacementException e) {
                        System.out.println(e.getMessage());
                    }
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

        for (int i = 0; i < 1; ++i) {
            Coordinates[] coords = new Coordinates[2];

            while (!validInput) {
                System.out.println("Введите координаты " + (i + 1) + " двухпалубного коробля (формат: x,y;x,y)");
                String line = scanner.nextLine();

                if (line.length() != 7 || line.charAt(1) != ',' || line.charAt(3) != ';' || line.charAt(5) != ',')
                    System.out.println("Координаты неверные. Правильный формат: x,y");

                else {
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
                            throw new InvalidShipPlacementException("Корабль должен располагаться либо вертикально, либо горизонтально!");
                        }
                        if (field[coords[0].y][coords[0].x] != FieldUnits.EMPTY ||
                                field[coords[1].y][coords[1].x] != FieldUnits.EMPTY) {
                            throw new InvalidShipPlacementException("Корабли должны располагаться друг от друга на расстоянии 1 клетки!");
                        }

                        validInput = true;

                    } catch (NumberFormatException e) {
                        System.out.println("Координаты неверные. X и Y должны быть числами!");
                    } catch (InvalidShipPlacementException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            validInput = false;
            addShip(new DoubleDeckShip(coords));

            printField();
        }
    }

    public void placeThreeDeckShips() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        for (int i = 0; i < 1; ++i) {
            Coordinates[] coords = new Coordinates[3];

            while (!validInput) {
                System.out.println("Введите координаты " + (i + 1) + " трёхпалубного коробля (формат: x,y;x,y;x,y)");
                String line = scanner.nextLine();

                if (line.length() != 11 || line.charAt(1) != ',' || line.charAt(3) != ';' || line.charAt(5) != ','
                        || line.charAt(7) != ';' || line.charAt(9) != ',')
                    System.out.println("Координаты неверные. Правильный формат: x,y");
                else {
                    String[] coordinates = line.split(";");
                    String[] coords1 = coordinates[0].split(",");
                    String[] coords2 = coordinates[1].split(",");
                    String[] coords3 = coordinates[2].split(",");

                    try {
                        for (int j = 0; j < coords.length; ++j) {
                            coords[j] = new Coordinates();
                        }

                        coords[0].x = Integer.parseInt(coords1[0]);
                        coords[0].y = Integer.parseInt(coords1[1]);
                        coords[1].x = Integer.parseInt(coords2[0]);
                        coords[1].y = Integer.parseInt(coords2[1]);
                        coords[2].x = Integer.parseInt(coords3[0]);
                        coords[2].y = Integer.parseInt(coords3[1]);

                        int xDiff = 0;
                        int yDiff = 0;

                        for (int j = 0; j < 2; j++) {
                            xDiff = Math.abs(coords[j].x - coords[j + 1].x);
                            yDiff = Math.abs(coords[j].y - coords[j + 1].y);

                            if (xDiff > 1 || yDiff > 1 || xDiff == yDiff) {
                                throw new InvalidShipPlacementException("Корабль должен располагаться либо вертикально, либо горизонтально!");
                            }
                        }

                        if (field[coords[0].y][coords[0].x] != FieldUnits.EMPTY ||
                                field[coords[1].y][coords[1].x] != FieldUnits.EMPTY ||
                                field[coords[2].y][coords[2].x] != FieldUnits.EMPTY) {
                            throw new InvalidShipPlacementException("Корабли должны располагаться друг от друга на расстоянии 1 клетки!");
                        }

                        validInput = true;

                    } catch (NumberFormatException e) {
                        System.out.println("Координаты неверные. X и Y должны быть числами!");
                    } catch (InvalidShipPlacementException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            validInput = false;
            addShip(new ThreeDeckShip(coords));

            printField();
        }
    }

    public void placeFourDeckShips() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        Coordinates[] coords = new Coordinates[4];

        while (!validInput) {
            System.out.println("Введите координаты четырёхпалубного коробля (формат: x,y;x,y;x,y;x,y)");
            String line = scanner.nextLine();

            if (line.length() != 15 || line.charAt(1) != ',' || line.charAt(3) != ';' || line.charAt(5) != ','
                    || line.charAt(7) != ';' || line.charAt(9) != ','
                    || line.charAt(11) != ';' || line.charAt(13) != ',')
                System.out.println("Координаты неверные. Правильный формат: x,y");
            else {
                String[] coordinates = line.split(";");
                String[] coords1 = coordinates[0].split(",");
                String[] coords2 = coordinates[1].split(",");
                String[] coords3 = coordinates[2].split(",");
                String[] coords4 = coordinates[3].split(",");

                try {
                    for (int j = 0; j < coords.length; ++j) {
                        coords[j] = new Coordinates();
                    }

                    coords[0].x = Integer.parseInt(coords1[0]);
                    coords[0].y = Integer.parseInt(coords1[1]);
                    coords[1].x = Integer.parseInt(coords2[0]);
                    coords[1].y = Integer.parseInt(coords2[1]);
                    coords[2].x = Integer.parseInt(coords3[0]);
                    coords[2].y = Integer.parseInt(coords3[1]);
                    coords[3].x = Integer.parseInt(coords4[0]);
                    coords[3].y = Integer.parseInt(coords4[1]);

                    int xDiff = 0;
                    int yDiff = 0;

                    for (int j = 0; j < 3; j++) {
                        xDiff = Math.abs(coords[j].x - coords[j + 1].x);
                        yDiff = Math.abs(coords[j].y - coords[j + 1].y);

                        if (xDiff > 1 || yDiff > 1 || xDiff == yDiff) {
                            throw new InvalidShipPlacementException("Корабль должен располагаться либо вертикально, либо горизонтально!");
                        }
                    }

                    if (field[coords[0].y][coords[0].x] != FieldUnits.EMPTY ||
                            field[coords[1].y][coords[1].x] != FieldUnits.EMPTY ||
                            field[coords[2].y][coords[2].x] != FieldUnits.EMPTY ||
                            field[coords[3].y][coords[3].x] != FieldUnits.EMPTY) {
                        throw new InvalidShipPlacementException("Корабли должны располагаться друг от друга на расстоянии 1 клетки!");
                    }

                    validInput = true;

                } catch (NumberFormatException e) {
                    System.out.println("Координаты неверные. X и Y должны быть числами!");
                } catch (InvalidShipPlacementException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        validInput = false;
        addShip(new FourDeckShip(coords));

        printField();

    }
}
