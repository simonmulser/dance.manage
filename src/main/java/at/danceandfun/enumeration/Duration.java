package at.danceandfun.enumeration;

public enum Duration {
    YEAR(0), WINTER(1), SUMMER(2);

    private Integer value;

    private Duration(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static Duration parse(Integer id) {
        Duration duration = null; // Default
        for (Duration item : Duration.values()) {
            if (item.getValue() == id) {
                duration = item;
                break;
            }
        }
        return duration;
    }
}
