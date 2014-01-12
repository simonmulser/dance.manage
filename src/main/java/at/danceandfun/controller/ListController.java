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
        logger.debug("SHOWLISTS");
        List<CourseParticipant> courseParticipants = courseParticipantManager
                .getCourseParticipantsByCount();
        List<Participant> participantsByCourseCount = new ArrayList<Participant>();
        for (CourseParticipant cp : courseParticipants) {
            Participant participant = participantManager.get(cp
                    .getParticipant().getPid());
            participant.setCourseParticipants(courseParticipantManager
                    .getEnabledDistinctCourseParticipants(participant));
            participantsByCourseCount.add(participant);
        }

        List<Course> courses = new ArrayList<Course>();
        for (Course course : courseManager.getEnabledList()) {
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
}
