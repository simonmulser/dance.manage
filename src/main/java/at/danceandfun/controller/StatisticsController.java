package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.service.CourseManager;
import at.danceandfun.service.CourseParticipantManager;
import at.danceandfun.service.ParticipantManager;
import at.danceandfun.service.StyleManager;

@Controller
@Transactional
@RequestMapping(value = "admin/statistics")
public class StatisticsController {

    private static Logger logger = Logger.getLogger(StatisticsController.class);

    @Autowired
    private ParticipantManager participantManager;
    @Autowired
    private CourseManager courseManager;
    @Autowired
    private StyleManager styleManager;
    @Autowired
    private CourseParticipantManager courseParticipantManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showStatistics(ModelMap map) {
        List<String> childrenPerStyleTemp = courseManager.getChildrenPerStyle(
                styleManager.getEnabledList(),
                courseParticipantManager.getEnabledList(), 2013);
        List<Integer> childrenPerStyleCount = new ArrayList<Integer>();
        List<String> childrenPerStyleList = new ArrayList<String>();
        for (String childrenPerStyle : childrenPerStyleTemp) {
            String[] splitResult = childrenPerStyle.split(",");
            childrenPerStyleList.add(splitResult[0]);
            childrenPerStyleCount.add(Integer.parseInt(splitResult[1]));
        }

        map.addAttribute("statistics", courseManager.getEnabledList());
        map.addAttribute("childrenPerStyleCount", childrenPerStyleCount);
        map.addAttribute("childrenPerStyleList", childrenPerStyleTemp);
        return "admin/statisticsView";
    }
}
