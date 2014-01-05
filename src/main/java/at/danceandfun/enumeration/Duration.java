package at.danceandfun.enumeration;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import at.danceandfun.util.AppContext;

public enum Duration {
    NONE(null), YEAR(0), WINTER(1), SUMMER(2);

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
