package at.danceandfun.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext appContext)
            throws BeansException {

        AppContext.setApplicationContext(appContext);
    }
}
