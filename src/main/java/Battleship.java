import ships.SingleDeckShip;

public class Battleship {
    public static void main(String[] args) {
        Field field = new Field();
        field.placeShips();

        field.printField();
    }
}
