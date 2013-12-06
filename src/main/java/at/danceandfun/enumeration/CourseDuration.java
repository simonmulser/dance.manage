package at.danceandfun.enumeration;

public enum CourseDuration {

    FIFTY(50), SEVENTYFIVE(75), HUNDRED(100);

    private Integer value;

    private CourseDuration(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static CourseDuration parse(Integer id) {
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
