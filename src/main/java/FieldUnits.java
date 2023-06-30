public enum FieldUnits {
    EMPTY("⬜"), SHIP("\uD83D\uDEA2");

    private String representation;

    private FieldUnits(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
