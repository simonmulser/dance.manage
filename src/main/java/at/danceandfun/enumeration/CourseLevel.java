package at.danceandfun.enumeration;

public enum CourseLevel {
    BEGINNER(0), INTERMEDIATE(1), ADVANCED(2);

    private Integer value;

    private CourseLevel(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static CourseLevel parse(Integer id) {
        CourseLevel courseLevel = null; // Default
        for (CourseLevel item : CourseLevel.values()) {
            if (item.getValue() == id) {
                courseLevel = item;
                break;
            }
        }
        return courseLevel;
    }
}
