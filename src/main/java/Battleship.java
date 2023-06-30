import ships.SingleDeckShip;

public class Battleship {
    public static void main(String[] args) {
//        SingleDeckShip ship1 = new SingleDeckShip(new int[]{1, 1});
//        SingleDeckShip ship2 = new SingleDeckShip(new int[]{3, 3});
//        SingleDeckShip ship3 = new SingleDeckShip(new int[]{0, 5});
//        SingleDeckShip ship4 = new SingleDeckShip(new int[]{9, 9});
//
//        Field field = new Field(new SingleDeckShip[]{ship1, ship2, ship3, ship4});

        Field field = new Field();
        field.placeShips();

        field.printField();
    }
}
