package at.danceandfun.enumeration;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import at.danceandfun.util.AppContext;

public enum SpectatorAmount {
    LOW(0), MEDIUM(1), HIGH(2);

    private Integer value;

    private SpectatorAmount(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static SpectatorAmount parse(Integer id) {
        SpectatorAmount spectatorAmount = null; // Default
        for (SpectatorAmount item : SpectatorAmount.values()) {
            if (item.getValue() == id) {
                spectatorAmount = item;
                break;
            }
        }
        return spectatorAmount;
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
