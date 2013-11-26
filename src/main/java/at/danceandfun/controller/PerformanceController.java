package at.danceandfun.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listPerformances(ModelMap map) {
        logger.debug("LIST performances with id " + performance.getPerid());
        map.addAttribute("performance", performance);
        map.addAttribute("performanceList", performanceManager.getEnabledList());
        map.addAttribute("courseList",
                performanceManager.getEnabledList().get(0).getCourses());

        return "performanceView";
    }

    @RequestMapping(value = "/build", method = RequestMethod.POST)
    public String buildPerformance(ModelMap map) {
        logger.debug("BUILD performance");
        Map<Integer, Performance> plan = new HashMap<Integer, Performance>();

        GenerateCNF sat = new GenerateCNF();
        try {
            plan = sat.generatePerformance(courseManager.getEnabledList());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // map.addAttribute("courseListPerformance2", plan.get(1).getCourses());
        // map.addAttribute("courseListPerformance3", plan.get(1).getCourses());
        performance = plan.get(1);
        performance.setEnabled(true);

        map.addAttribute("courseListPerformance1", performance.getCourses());

        performanceManager.save(performance);

        performance = new Performance();

        return "redirect:/performance";
    }

    public void setPerformanceManager(PerformanceManager performanceManager) {
        this.performanceManager = performanceManager;
    }
}
