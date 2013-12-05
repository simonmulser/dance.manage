package at.danceandfun.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.service.CourseManager;
import at.danceandfun.service.ParticipantManager;

@Controller
@Transactional
@RequestMapping(value = "admin/statistics")
public class StatisticsController {

    private static Logger logger = Logger.getLogger(StatisticsController.class);

    @Autowired
    private ParticipantManager participantManager;

    @Autowired
    private CourseManager courseManager;

    private PagedListHolder myList;

    private boolean initialize = true;

    /*
     * @RequestMapping(value = "", method = RequestMethod.GET) public String
     * showLists(ModelMap map,
     * 
     * @RequestParam(value = "page", required = false) String page) {
     * logger.debug("SHOW STATISTICS");
     * 
     * if (initialize) { initialize = false; myList = new
     * PagedListHolder(courseManager.getEnabledList()); myList.setPageSize(10);
     * map.addAttribute("list", myList); return "admin/statisticsView"; } else {
     * if ("next".equals(page)) { myList.nextPage(); } else if
     * ("previous".equals(page)) { myList.previousPage(); }
     * map.addAttribute("list", myList); return "admin/statisticsView"; }
     * 
     * }
     */

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showStatistics(ModelMap map) {

        map.addAttribute("statistics", courseManager.getEnabledList());
        return "admin/statisticsView";
    }

}
