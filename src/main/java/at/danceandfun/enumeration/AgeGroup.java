package at.danceandfun.enumeration;

public enum AgeGroup {
    SMALL(0), MEDIUM(1), BIG(2);
    
    private Integer value;

    private AgeGroup(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static AgeGroup parse(Integer id) {
        AgeGroup ageGroup = null; // Default
        for (AgeGroup item : AgeGroup.values()) {
            if (item.getValue() == id) {
                ageGroup = item;
                break;
            }
        }
        return ageGroup;
    }
}
