package at.danceandfun.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap map) {
        logger.debug("LOGIN");

        return "login";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String home(ModelMap map) {
        logger.debug("HOME");

        return "listView";
    }
}
