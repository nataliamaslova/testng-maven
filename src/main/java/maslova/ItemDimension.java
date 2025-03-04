package maslova;

public enum ItemDimension {
    LARGE(200),
    SMALL(100);

    private int costIncrease;

    ItemDimension(int costIncrease) {
        this.costIncrease = costIncrease;
    }

    public int getCostIncrease() {
        return costIncrease;
    }
}
