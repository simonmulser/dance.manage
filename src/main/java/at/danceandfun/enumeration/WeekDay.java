package at.danceandfun.enumeration;

public enum WeekDay {
    MONDAY(0), TUESDAY(1), WEDNESDAY(2), THURSDAY(3), FRIDAY(4);

    private Integer value;

    private WeekDay(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static WeekDay parse(Integer id) {
        WeekDay weekDay = null; // Default
        for (WeekDay item : WeekDay.values()) {
            if (item.getValue() == id) {
                weekDay = item;
                break;
            }
        }
        return weekDay;
    }
}
