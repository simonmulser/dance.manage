package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.CourseParticipantManager;
import at.danceandfun.service.ParticipantManager;

@Controller
@Transactional
@RequestMapping(value = "admin/lists")
public class ListController {

    private static Logger logger = Logger.getLogger(ListController.class);

    @Autowired
    private ParticipantManager participantManager;

    @Autowired
    private CourseParticipantManager courseParticipantManager;

    @Autowired
    private CourseManager courseManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showLists(ModelMap map) {
        logger.info("SHOWLISTS");
        List<CourseParticipant> courseParticipants = courseParticipantManager
                .getCourseParticipantsByCount();
        List<Participant> participantsByCourseCount = new ArrayList<Participant>();
        for (CourseParticipant cp : courseParticipants) {
            Participant participant = participantManager.get(cp
                    .getParticipant().getPid());
            participant.setCourseParticipants(courseParticipantManager
                    .getEnabledDistinctCourseParticipants(participant));
            if (participant.getCourseParticipants().size() > 1) {
                participantsByCourseCount.add(participant);
            }
        }

        List<Course> courses = new ArrayList<Course>();
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);
        criteria.addOrder(Order.asc("name"));
        criteria.add(Restrictions.eq("inPerformance", true));
        for (Course course : courseManager.getEnabledListWithCriteria(criteria)) {
            if (course.getCourseParticipants().size() > 0) {
                course.setCourseParticipants(courseParticipantManager
                        .getEnabledDistinctCourseParticipants(course));
            }
            courses.add(course);
        }
        map.addAttribute("participantsByCourseCount", participantsByCourseCount);
        map.addAttribute("participantsByNumberOfSiblings",
                participantManager.getParticipantsByNumberOfSiblings());
        map.addAttribute("courses", courses);
        return "admin/listView";

    }

    @RequestMapping(value = "/viewPerformanceDetailPdf", method = RequestMethod.GET)
    public ModelAndView viewPerformanceDetailPdf() {
        logger.info("Creating performance details pdf");

        List<Course> courses = new ArrayList<Course>();
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);
        criteria.addOrder(Order.asc("name"));
        criteria.add(Restrictions.eq("inPerformance", true));
        for (Course course : courseManager.getEnabledListWithCriteria(criteria)) {
            if (course.getCourseParticipants().size() > 0) {
                course.setCourseParticipants(courseParticipantManager
                        .getEnabledDistinctCourseParticipants(course));
            }
            courses.add(course);
        }
        HashMap<String, Object> map = new HashMap<String, Object>(1);
        map.put("courses", courses);
        return new ModelAndView("viewPerformanceDetailPdf", map);
    }

    @RequestMapping(value = "/viewParticipantsByCourseCountPdf", method = RequestMethod.GET)
    public ModelAndView viewParticipantsByCourseCountPdf() {
        logger.info("Creating participants by course count pdf");

        List<CourseParticipant> courseParticipants = courseParticipantManager
                .getCourseParticipantsByCount();
        List<Participant> participantsByCourseCount = new ArrayList<Participant>();
        for (CourseParticipant cp : courseParticipants) {
            Participant participant = participantManager.get(cp
                    .getParticipant().getPid());
            participant.setCourseParticipants(courseParticipantManager
                    .getEnabledDistinctCourseParticipants(participant));
            if (participant.getCourseParticipants().size() > 1) {
                participantsByCourseCount.add(participant);
            }
        }
        HashMap<String, Object> map = new HashMap<String, Object>(1);
        map.put("participantsByCourseCount", participantsByCourseCount);
        return new ModelAndView("viewParticipantsByCourseCountPdf", map);
    }

    @RequestMapping(value = "/viewParticipantsByNumberOfSiblingsPdf", method = RequestMethod.GET)
    public ModelAndView viewParticipantsByNumberOfSiblingsPdf() {
        logger.info("Creating participants by course count pdf");

        HashMap<String, Object> map = new HashMap<String, Object>(1);
        map.put("participantsByNumberOfSiblings",
                participantManager.getParticipantsByNumberOfSiblings());
        return new ModelAndView("viewParticipantsByNumberOfSiblingsPdf", map);
    }
}
