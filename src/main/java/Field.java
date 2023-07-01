import ships.SingleDeckShip;

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
        int[] coords = ship.getCoords();

        field[coords[0]][coords[1]] = FieldUnits.SHIP;
        if(coords[0] != 0) {
            field[coords[0] - 1][coords[1] - 1] = FieldUnits.AUREOLE;
            field[coords[0] - 1][coords[1]] = FieldUnits.AUREOLE;
            field[coords[0] - 1][coords[1] + 1] = FieldUnits.AUREOLE;
        }
        if(coords[0] != 9) {
            field[coords[0] + 1][coords[1] - 1] = FieldUnits.AUREOLE;
            field[coords[0] + 1][coords[1]] = FieldUnits.AUREOLE;
            field[coords[0] + 1][coords[1] + 1] = FieldUnits.AUREOLE;
        }
        if(coords[1] != 0) {
            field[coords[0] - 1][coords[1] - 1] = FieldUnits.AUREOLE;
            field[coords[0]][coords[1] - 1] = FieldUnits.AUREOLE;
            field[coords[0] + 1][coords[1] - 1] = FieldUnits.AUREOLE;
        }
        if(coords[1] != 9) {
            field[coords[0] - 1][coords[1] + 1] = FieldUnits.AUREOLE;
            field[coords[0]][coords[1] + 1] = FieldUnits.AUREOLE;
            field[coords[0] + 1][coords[1] + 1] = FieldUnits.AUREOLE;
        }
    }

    public void placeShips() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        for (int i = 0; i < 3; i++) {
            int[] coords = new int[2];

            while(!validInput) {
                System.out.println("Введи координаты " + (i + 1) + " однопалубного коробля (формат: x,y)");
                String line = scanner.nextLine();

                if(line.length() == 3 && line.charAt(1) == ',') {
                    String[] stringNumbers = line.split(",");
                    try {
                        coords[0] = Integer.parseInt(stringNumbers[0]);
                        coords[1] = Integer.parseInt(stringNumbers[1]);

                        if(field[coords[0]][coords[1]] == FieldUnits.AUREOLE)
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
