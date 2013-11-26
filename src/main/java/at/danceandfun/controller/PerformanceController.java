package at.danceandfun.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Performance;
import at.danceandfun.sat.GenerateCNF;
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
    private Performance tempPerformance1 = new Performance();
    private Performance tempPerformance2 = new Performance();
    private Performance tempPerformance3 = new Performance();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listPerformances(ModelMap map) {
        logger.debug("LIST performances with id " + performance.getPerid());
        map.addAttribute("performance", performance);
        map.addAttribute("performanceList1", tempPerformance1.getCourses());
        map.addAttribute("performanceList2", tempPerformance2.getCourses());
        map.addAttribute("performanceList3", tempPerformance3.getCourses());

        return "performanceView";
    }

    @RequestMapping(value = "/build", method = RequestMethod.POST)
    public String buildPerformance(ModelMap map) {
        logger.debug("BUILD performance");
        Map<Integer, Performance> plan = new HashMap<Integer, Performance>();
        List<Course> courses = courseManager.getEnabledList();
        Collections.shuffle(courses);

        GenerateCNF sat = new GenerateCNF();
        try {
            plan = sat.generatePerformance(courses);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tempPerformance1 = plan.get(1);
        tempPerformance2 = plan.get(2);
        tempPerformance3 = plan.get(3);
        performanceManager.save(tempPerformance1);
        performanceManager.save(tempPerformance2);
        performanceManager.save(tempPerformance3);
        performance = new Performance();

        return "redirect:/performance";
    }

    public void setPerformanceManager(PerformanceManager performanceManager) {
        this.performanceManager = performanceManager;
    }
}
