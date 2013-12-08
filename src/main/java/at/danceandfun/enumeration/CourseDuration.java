package at.danceandfun.enumeration;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import at.danceandfun.util.AppContext;

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
