package at.danceandfun.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Performance;
import at.danceandfun.exception.SatException;
import at.danceandfun.sat.GenerateSatSolution;
import at.danceandfun.sat.SatValidator;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.ParticipantManager;
import at.danceandfun.service.PerformanceManager;

//import at.danceandfund.exception.SatException;

@Controller
@RequestMapping(value = "admin/performance")
public class PerformanceController {

    private static Logger logger = Logger
            .getLogger(PerformanceController.class);

    @Autowired
    private PerformanceManager performanceManager;
    @Autowired
    private CourseManager courseManager;
    @Autowired
    private ParticipantManager participantManager;

    private Performance performance = new Performance();
    private Performance tempPerformance1 = new Performance();
    private Performance tempPerformance2 = new Performance();
    private Performance tempPerformance3 = new Performance();
    private Map<Integer, Performance> performancePlan;
    private boolean balletRestriction = true;
    private boolean twoBreaksRestriction = true;
    private boolean advancedAtEndRestriction = true;
    private boolean balancedAmountOfSpectators = true;
    private boolean balancedAgeGroup = true;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listPerformances(ModelMap map) {
        logger.debug("LIST performances with id " + performance.getPerid());

        map.addAttribute("performance", performance);
        map.addAttribute("performanceList1", tempPerformance1.getCourses());
        map.addAttribute("performanceList2", tempPerformance2.getCourses());
        map.addAttribute("performanceList3", tempPerformance3.getCourses());
        map.addAttribute("balletRestriction", balletRestriction);
        map.addAttribute("twoBreaksRestriction", twoBreaksRestriction);
        map.addAttribute("advancedAtEndRestriction", advancedAtEndRestriction);
        map.addAttribute("balancedAmountOfSpectators",
                balancedAmountOfSpectators);
        map.addAttribute("balancedAgeGroup", balancedAgeGroup);

        return "admin/performanceView";
    }

    @RequestMapping(value = "/build", method = RequestMethod.POST)
    public String buildPerformance(ModelMap map, HttpServletRequest request) {
        logger.debug("BUILD performance");
        performancePlan = new HashMap<Integer, Performance>();
        List<Course> courses = courseManager.getEnabledList();
        List<Participant> participantList = participantManager.getEnabledList();

        setCheckedRestrictions(request);

        Collections.shuffle(courses);

        GenerateSatSolution sat = new GenerateSatSolution();

        while (true) {
            try {
                performancePlan = sat.generatePerformance(courses,
                        participantList, balletRestriction,
                        twoBreaksRestriction, advancedAtEndRestriction,
                        balancedAmountOfSpectators, balancedAgeGroup);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SatException s) {
                System.out.println(s.getMessage());
                Collections.shuffle(courses);
                continue;
            }
        }

        SatValidator validator = new SatValidator(performancePlan,
                participantList);

        performancePlan = validator.validatePerformancePlan();

        tempPerformance1 = performancePlan.get(1);
        tempPerformance2 = performancePlan.get(2);
        tempPerformance3 = performancePlan.get(3);
        // performanceManager.save(tempPerformance1);
        // performanceManager.save(tempPerformance2);
        // performanceManager.save(tempPerformance3);
        performance = new Performance();

        return "redirect:/admin/performance";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public String buildPerformance() {
        logger.debug("VALDIATE performancePlan");

        return "redirect:/admin/performance";
    }

    private void setCheckedRestrictions(HttpServletRequest request) {
        if (request.getParameter("CheckboxBallet") != null) {
            this.balletRestriction = true;
        } else {
            this.balletRestriction = false;
        }
        if (request.getParameter("CheckboxTwoCourseBreak") != null) {
            this.twoBreaksRestriction = true;
        } else {
            this.twoBreaksRestriction = false;
        }
        if (request.getParameter("CheckboxAdvancedAtEnd") != null) {
            this.advancedAtEndRestriction = true;
        } else {
            this.advancedAtEndRestriction = false;
        }
        if (request.getParameter("CheckboxBalancedSpectators") != null) {
            this.balancedAmountOfSpectators = true;
        } else {
            this.balancedAmountOfSpectators = false;
        }
        if (request.getParameter("CheckboxBalancedAgeGroup") != null) {
            this.balancedAgeGroup = true;
        } else {
            this.balancedAgeGroup = false;
        }

    }

    public void setPerformanceManager(PerformanceManager performanceManager) {
        this.performanceManager = performanceManager;
    }
}
