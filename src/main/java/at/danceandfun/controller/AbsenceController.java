package at.danceandfun.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.danceandfun.entity.Course;
import at.danceandfun.service.CourseManager;

@Controller
@RequestMapping(value = "admin/absence")
public class AbsenceController {

    private static Logger logger = Logger.getLogger(AbsenceController.class);

    @Autowired
    private CourseManager courseManager;

    private Course actualCourse;

    @PostConstruct
    public void init() {
        logger.info("INIT AbsenceController");
        actualCourse = new Course();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showEditPassword(ModelMap map) {
        logger.info("showAbsenceView with "
                + actualCourse.getAppointments().size() + " appointments");
        return "admin/absenceView";
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    public @ResponseBody
    List<Course> getParticipants(@RequestParam("term") String query) {
        return courseManager.searchForCourses(query);
    }

    @RequestMapping(value = "/getDetailsForCourse/{cid}")
    public String detailsForCourse(@PathVariable("cid") Integer cid,
            ModelMap map) {
        logger.info("Show absences for course: " + cid);
        actualCourse = courseManager.get(cid);
        logger.info("Course has "
                + actualCourse.getAppointments().size() + " appointments");
        map.put("actualCourse", actualCourse);
        return "admin/absenceView";
    }
}
