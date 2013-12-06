package at.danceandfun.enumeration;

public enum SpectatorAmount {
    LOW(0), MEDIUM(1), HIGH(2);

    private Integer value;

    private SpectatorAmount(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static SpectatorAmount parse(Integer id) {
        SpectatorAmount spectatorAmount = null; // Default
        for (SpectatorAmount item : SpectatorAmount.values()) {
            if (item.getValue() == id) {
                spectatorAmount = item;
                break;
            }
        }
        return spectatorAmount;
    }
}
