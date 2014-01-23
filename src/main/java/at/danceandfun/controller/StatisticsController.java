package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import at.danceandfun.entity.Course;
import at.danceandfun.enumeration.CourseLevel;
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
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);
        criteria.addOrder(Order.desc("year"));
        criteria.addOrder(Order.asc("name"));
        map.addAttribute("statistics",
                courseManager.getListByCriteria(criteria));

        List<String> participantsPerStyle = courseManager
                .getParticipantPerStyle(styleManager.getEnabledList(),
                        courseParticipantManager.getEnabledList(), 2013);
        map.addAttribute("participantsPerStyleList", participantsPerStyle);

        List<String> courseLevels = new ArrayList<String>();
        for (CourseLevel level : CourseLevel.values()) {
            courseLevels.add(level.getLabel());
        }
        map.addAttribute("courseLevels", courseLevels);

        List<Long> participantsPerLevelActualYear = courseManager
                .getParticipantPerLevel(2013);
        List<Long> participantsPerLevelLastYear = courseManager
                .getParticipantPerLevel(2012);
        if (Collections.max(participantsPerLevelActualYear) > Collections
                .max(participantsPerLevelLastYear)) {
            map.addAttribute("maxScale",
                    Collections.max(participantsPerLevelActualYear));
        } else {
            map.addAttribute("maxScale",
                    Collections.max(participantsPerLevelLastYear));
        }
        map.addAttribute("participantsPerLevelActualYear",
                participantsPerLevelActualYear);
        map.addAttribute("participantsPerLevelLastYear",
                participantsPerLevelLastYear);
        return "admin/statisticsView";
    }

    @RequestMapping(value = "/viewCourseStatisticsPdf", method = RequestMethod.GET)
    public ModelAndView viewCourseStatisticsPdf() {
        logger.debug("Creating course statistics pdf");

        HashMap<String, Object> map = new HashMap<String, Object>(1);
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);
        criteria.addOrder(Order.desc("year"));
        criteria.addOrder(Order.asc("name"));
        map.put("statistics", courseManager.getListByCriteria(criteria));
        return new ModelAndView("viewCourseStatisticsPdf", map);
    }
}
