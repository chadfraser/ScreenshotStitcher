package zoom;

public enum ZoomValue {
    FIT_TO_SCREEN ("Fit to screen", 0.0),
    TEN_PERCENT ("10%", 0.1),
    TWENTY_FIVE_PERCENT ("25%", 0.25),
    FIFTY_PERCENT ("50%", 0.5),
    ONE_HUNDRED_PERCENT ("100%", 1.0),
    TWO_HUNDRED_PERCENT ("200%", 2.0);

    private final String name;
    private final double percentage;

    ZoomValue(String name, double percentage) {
        this.name = name;
        this.percentage = percentage;
    }

    public String toString() {
        return name;
    }

    public double getPercentage() {
        return percentage;
    }
}
