package at.danceandfun.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin/search")
public class SearchController {

    private static Logger logger = Logger.getLogger(SearchController.class);

    @RequestMapping(value = "request", method = RequestMethod.GET)
    public String requestSearch(@RequestParam("searchTerm") String searchTerm,
            ModelMap model) {
        logger.debug("Search in DB with term: " + searchTerm);
        return "admin/index";
    }
}
