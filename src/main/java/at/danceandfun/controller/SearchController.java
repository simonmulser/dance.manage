package at.danceandfun.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.danceandfun.entity.EntityBase;
import at.danceandfun.service.SearchManager;
import at.danceandfun.util.SearchResult;

@Controller
@RequestMapping(value = "/admin/search")
public class SearchController {

    private static Logger logger = Logger.getLogger(SearchController.class);

    @Autowired
    private SearchManager searchManager;

    private SearchResult searchResults;

    @RequestMapping(value = "request", method = RequestMethod.GET)
    public String requestSearch(@RequestParam("query") String query,
            ModelMap model) {
        logger.info("Search in DB with term: " + query);
        
        fillSearchObject(query);
        model.put("searchResults", searchResults);

        return "admin/searchView";
    }

    @RequestMapping(value = "/searchQuery", method = RequestMethod.GET)
    public @ResponseBody
    List<EntityBase> searchQuery(@RequestParam("term") String query) {
        logger.info("Search in DB with term: " + query);
        fillSearchObject(query);
        return searchResults.getAllResults();
    }

    private void fillSearchObject(String query) {
        searchResults = new SearchResult();
        searchResults.setSearchedParticipants(searchManager
                .searchParticipants(query));
        searchResults.setSearchedTeachers(searchManager.searchTeachers(query));
        searchResults.setSearchedParents(searchManager.searchParents(query));
        searchResults.setSearchedCourses(searchManager.searchCourses(query));
    }
}
