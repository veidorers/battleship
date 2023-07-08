package com.example;

import com.example.ships.Ship;

import java.util.Random;
import java.util.Scanner;

public class Battleship {
    private static Player p1;
    private static Player p2;
    private static String nextPlayer;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Первый игрок, введи своё имя: ");
        p1 = new Player(scanner.nextLine());
        System.out.println("Привет, " + p1.name + "!");

        System.out.println();


        System.out.println("Второй игрок, введи своё имя: ");
        p2 = new Player(scanner.nextLine());
        System.out.println("Привет, " + p2.name + "!");

        Thread.sleep(500);
        clearTerminal();

        fillFields();

        System.out.println("Начинаем игру!");
        Thread.sleep(1500);
        play();
    }

    private static void fillFields() {
        System.out.println("Начнём расставлять корабли для " + p1.name + "! " + p2.name + ", отвернись!");
        p1.field = new Field();
        p1.field.placeShips();

        clearTerminal();

        System.out.println("Начнём расставлять корабли для " + p2.name + "! " + p1.name + ", отвернись!");
        p2.field = new Field();
        p2.field.placeShips();

        p1.enemy = p2;
        p2.enemy = p1;

        clearTerminal();
    }

    private static void clearTerminal() {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }

    private static void play() throws InterruptedException {
        Random random = new Random();

        if (random.nextInt(2) == 0) nextPlayer = p1.name;
        else nextPlayer = p2.name;

        clearTerminal();
        while(p1.sunkShips < 4 && p2.sunkShips < 4) {
            if (nextPlayer.equals(p1.name)) hit(p1);
            else hit(p2);
        }

        String winner = (p1.sunkShips == 4) ? p1.name : p2.name;

        System.out.println("Победил " + winner + "! Поздравляем!");


    }

    private static void hit(Player p) throws InterruptedException {
        System.out.println("Ход игрока " + p.name + "!");
        Field field = p.enemy.field;

        p.enemyField.printField();

        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        Coordinates coords = null;

        while (!validInput) {
            System.out.println("Введите координаты удара (формат x,y): ");
            String line = scanner.nextLine();

            if (line.length() != 3 || line.charAt(1) != ',') {
                System.out.println("Неверные координаты. Правильный формат: x,y");
            } else {
                String[] coordsArr = line.split(",");
                try {
                    coords = new Coordinates();
                    coords.x = Integer.parseInt(coordsArr[0]);
                    coords.y = Integer.parseInt(coordsArr[1]);

                    if (p.enemyField.getField()[coords.y][coords.x] != FieldUnits.EMPTY)
                        System.out.println("Вы не можете атаковать это поле!");
                    else
                        validInput = true;

                } catch (NumberFormatException e) {
                    System.out.println("Координаты неверные. X и Y должны быть числами!");
                }
            }
        }
        if(field.getField()[coords.y][coords.x] == FieldUnits.SHIP) {
            nextPlayer = (p == p1 ? p1.name : p2.name);
            field.getField()[coords.y][coords.x] = FieldUnits.HIT;
            p.enemyField.getField()[coords.y][coords.x] = FieldUnits.HIT;

            if (field.getField()[Math.max(coords.y - 1, 0)][coords.x] == FieldUnits.SHIP ||
                    field.getField()[Math.min(coords.y + 1, 9)][coords.x] == FieldUnits.SHIP ||
                    field.getField()[coords.y][Math.max(coords.x - 1, 0)] == FieldUnits.SHIP ||
                    field.getField()[coords.y][Math.min(coords.x + 1, 9)] == FieldUnits.SHIP)
                System.out.println("Попал!");
            else {
                p.sunkShips++;
                System.out.println("Потопил!");
                sinkShip(p, coords);
            }
        }
        else {
            System.out.println("Мимо!");
            p.enemyField.getField()[coords.y][coords.x] = FieldUnits.MISS;
            nextPlayer = (p == p1 ? p2.name : p1.name);
        }
        Thread.sleep(1100);

        clearTerminal();
    }

    public static void sinkShip(Player p, Coordinates c) {
        Ship ship = p.enemy.field.findShipByCoords(c);

        Coordinates[] coords = ship.getCoords();

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
                if(p.enemyField.getField()[j][i] == FieldUnits.HIT)
                    continue;

                p.enemyField.getField()[j][i] = FieldUnits.AUREOLE;
            }
        }


    }
}
