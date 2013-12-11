package at.danceandfun.enumeration;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import at.danceandfun.util.AppContext;

public enum AgeGroup {
    SMALL(0), MEDIUM(1), BIG(2), PAUSE(3);

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

    public String getLabel() {
        Locale locale = LocaleContextHolder.getLocale();
        return AppContext.getApplicationContext().getMessage(
                getI18nIdentifier(), null, locale);
    }

    public String getI18nIdentifier() {
        return this.getClass().getSimpleName().toLowerCase() + "."
                + parse(value).name().toLowerCase();
    }
}
