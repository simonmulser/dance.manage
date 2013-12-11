package at.danceandfun.enumeration;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import at.danceandfun.util.AppContext;

public enum CourseLevel {
    BEGINNER(0), INTERMEDIATE(1), ADVANCED(2), PAUSE(3);

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
