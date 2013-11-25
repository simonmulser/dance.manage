package at.danceandfun.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.entity.Performance;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.PerformanceManager;

@Controller
@RequestMapping(value = "/performance")
public class PerformanceController {

    private static Logger logger = Logger
            .getLogger(PerformanceController.class);

    @Autowired
    private PerformanceManager performanceManager;
    @Autowired
    private CourseManager courseManager;

    private Performance performance = new Performance();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listPerformances(ModelMap map) {
        logger.debug("LIST performances with id " + performance.getPerid());
        map.addAttribute("performance", performance);
        map.addAttribute("performanceList", performanceManager.getEnabledList());
        map.addAttribute("courseList",
                performanceManager.getEnabledList().get(0).getCourses());

        return "performanceView";
    }

    // @RequestMapping(value = "/add", method = RequestMethod.POST)
    // public String addCourse(@ModelAttribute(value = "course") Course course,
    // BindingResult result) {
    // logger.debug("ADD Course with id " + course.getCid());
    // course.setEnabled(true);
    // courseManager.save(course);
    // course = new Course();
    // return "redirect:/course";
    // }

    @RequestMapping(value = "/build", method = RequestMethod.POST)
    public String buildPerformance(ModelMap map) {

        return "redirect:/performance";
    }

    public void setPerformanceManager(PerformanceManager performanceManager) {
        this.performanceManager = performanceManager;
    }
}
