package at.danceandfun.enumeration;

public enum CourseDuration {

    FIFTY(1), SEVENTYFIVE(75), HUNDRED(100);

    private int value;

    private CourseDuration(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static CourseDuration parse(int id) {
        CourseDuration courseDuration = null; // Default
        for (CourseDuration item : CourseDuration.values()) {
            if (item.getValue() == id) {
                courseDuration = item;
                break;
            }
        }
        return courseDuration;
    }
}
