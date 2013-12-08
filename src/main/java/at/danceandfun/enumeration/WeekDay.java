package at.danceandfun.enumeration;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import at.danceandfun.util.AppContext;

public enum WeekDay {
    MONDAY(0), TUESDAY(1), WEDNESDAY(2), THURSDAY(3), FRIDAY(4), SATURDAY(5), SUNDAY(
            6);

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
