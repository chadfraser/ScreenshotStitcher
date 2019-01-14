public enum ZoomValue {
    FIT_TO_SCREEN ("Fit to screen"),
    TEN_PERCENT ("10%"),
    TWENTY_FIVE_PERCENT ("25%"),
    FIFTY_PERCENT ("50%"),
    ONE_HUNDRED_PERCENT ("100%"),
    TWO_HUNDRED_PERCENT ("200%");

    private final String name;

    ZoomValue(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
