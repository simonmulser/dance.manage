package at.danceandfun.util;

import org.springframework.context.ApplicationContext;

public class AppContext {

    private static ApplicationContext appContext;

    public static void setApplicationContext(
            ApplicationContext applicationContext) {
        appContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return appContext;
    }
}
