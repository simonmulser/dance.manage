package at.danceandfun.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.danceandfun.service.SearchManager;

@Controller
@RequestMapping(value = "/admin/search")
public class SearchController {

    private static Logger logger = Logger.getLogger(SearchController.class);

    @Autowired
    private SearchManager searchManager;

    @RequestMapping(value = "request", method = RequestMethod.GET)
    public String requestSearch(@RequestParam("query") String query,
            ModelMap model) {
        logger.debug("Search in DB with term: " + query);
        model.put("searchedParticipants",
                searchManager.searchParticipants(query));
        model.put("searchedTeachers", searchManager.searchTeachers(query));
        model.put("searchedParents", searchManager.searchParents(query));
        model.put("searchedCourses", searchManager.searchCourses(query));
        return "admin/searchView";
    }
}
