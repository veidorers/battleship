public enum FieldUnits {
    EMPTY("\uD83D\uDD33"), SHIP("\uD83D\uDEA2"), AUREOLE("\uD83D\uDFE6"), HIT("\uD83D\uDFE5");

    private String representation;

    private FieldUnits(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
