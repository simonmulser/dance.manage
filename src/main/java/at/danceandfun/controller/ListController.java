package at.danceandfun.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.service.CourseManager;
import at.danceandfun.service.ParticipantManager;

@Controller
@Transactional
@RequestMapping(value = "/lists")
public class ListController {

    private static Logger logger = Logger.getLogger(ListController.class);

    @Autowired
    private ParticipantManager participantManager;

    @Autowired
    private CourseManager courseManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showLists(ModelMap map) {
        logger.debug("SHOWLISTS");
        map.addAttribute("participantsByNumberOfCourses",
                participantManager.getParticipantsByNumberOfCourses());
        map.addAttribute("participantsByNumberOfSiblings",
                participantManager.getParticipantsByNumberOfSiblings());
        map.addAttribute("courses", courseManager.getEnabledList());
        return "listView";
    }
}
