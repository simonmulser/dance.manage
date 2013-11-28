package at.danceandfun.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
public class DefaultController {

    private static Logger logger = Logger.getLogger(DefaultController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap map) {
        logger.debug("LOGIN");

        return "login";
    }

    @RequestMapping("/default")
    public String defaultAfterLogin(WebRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        if (request.isUserInRole("ROLE_PARTICIPANT")) {
            return "redirect:/participant";
        }
        if (request.isUserInRole("ROLE_PARENT")) {
            // TODO
            logger.debug("TODO");
            return "redirect:/parent";
        }
        if (request.isUserInRole("ROLE_TEACHER")) {
            // TODO
            logger.debug("TODO");
            return "redirect:/teacher";
        }
        if (request.isUserInRole("ROLE_SUPERUSER")) {
            return "redirect:/admin";
        }

        logger.error("role does not exists. redirect to login!");
        // TODO: log user out! he is in a role which not exists
        return "redirect:/login";
    }
}
