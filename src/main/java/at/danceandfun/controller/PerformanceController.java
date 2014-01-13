package at.danceandfun.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import at.danceandfun.entity.PerformancePlan;
import at.danceandfun.exception.SatException;
import at.danceandfun.sat.GenerateSatSolution;
import at.danceandfun.sat.SatValidator;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.ParticipantManager;
import at.danceandfun.service.PerformanceManager;
import at.danceandfun.service.PerformancePlanManager;

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
    @Autowired
    private PerformancePlanManager performancePlanManager;

    private Performance performance = new Performance();
    private Performance tempPerformance1 = new Performance();
    private Performance tempPerformance2 = new Performance();
    private Performance tempPerformance3 = new Performance();
    private Map<Integer, Performance> performancePlan2;
    private PerformancePlan performancePlan;
    private boolean balletRestriction = true;
    private boolean twoBreaksRestriction = true;
    private boolean advancedAtEndRestriction = true;
    private boolean balancedAmountOfSpectators = true;
    private boolean balancedAgeGroup = true;
    private boolean multipleGroupsSamePerformance = true;
    private boolean sibsSamePerformance = true;

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
        map.addAttribute("multipleGroupsSamePerformance",
                multipleGroupsSamePerformance);
        map.addAttribute("sibsSamePerformance", sibsSamePerformance);
        map.addAttribute("PerformancePlan", performancePlan);

        return "admin/performanceView";
    }

    @RequestMapping(value = "/build", method = RequestMethod.POST)
    public String buildPerformance(ModelMap map, HttpServletRequest request) {
        logger.debug("BUILD performance");
        performancePlan2 = new HashMap<Integer, Performance>();
        performancePlan = new PerformancePlan();
        List<Course> courses = courseManager.getEnabledList();
        List<Participant> participantList = participantManager.getEnabledList();

        setCheckedRestrictions(request);

        Collections.shuffle(courses);

        GenerateSatSolution sat = new GenerateSatSolution();

        while (true) {
            try {
                performancePlan2 = sat.generatePerformance(courses,
                        participantList, balletRestriction,
                        twoBreaksRestriction, advancedAtEndRestriction,
                        balancedAmountOfSpectators, balancedAgeGroup,
                        multipleGroupsSamePerformance, sibsSamePerformance);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SatException s) {
                System.out.println(s.getMessage());
                Collections.shuffle(courses);
                continue;
            }
        }

        SatValidator validator = new SatValidator(performancePlan2,
                participantList);
        performancePlan2 = validator.validatePerformancePlan();

        tempPerformance1 = performancePlan2.get(1);
        tempPerformance2 = performancePlan2.get(2);
        tempPerformance3 = performancePlan2.get(3);

        // delete dummy courses
        if (tempPerformance2.getCourses().get(0).getName().equals("Dummy")) {
            tempPerformance2.getCourses().remove(0);
            tempPerformance2.getCourseIds().remove(0);
        }
        if (tempPerformance3.getCourses().get(0).getName().equals("Dummy")) {
            tempPerformance3.getCourses().remove(0);
            tempPerformance3.getCourseIds().remove(0);
        }

        tempPerformance1.setEnabled(true);
        tempPerformance2.setEnabled(true);
        tempPerformance3.setEnabled(true);

        List<Performance> performances = new ArrayList<Performance>();
        performances.add(tempPerformance1);
        performances.add(tempPerformance2);
        performances.add(tempPerformance3);

        performancePlan.setPerformances(performances);

        performancePlanManager.persist(performancePlan);

        List<Performance> fetchedPerformances = performanceManager
                .getEnabledList();

        for (Performance cur : fetchedPerformances) {
            List<Course> courseList = new ArrayList<Course>();
            for (Integer curI : cur.getCourseIds()) {
                for (Course curC : courses) {
                    if (curC.getCid() == curI) {
                        courseList.add(curC);
                        continue;
                    }
                }
            }
            cur.setCourses(courseList);
        }

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
        if (request.getParameter("CheckboxMultipleGroupsSamePerformance") != null) {
            this.multipleGroupsSamePerformance = true;
        } else {
            this.multipleGroupsSamePerformance = false;
        }
        if (request.getParameter("CheckboxSibsSamePerformance") != null) {
            this.sibsSamePerformance = true;
        } else {
            this.sibsSamePerformance = false;
        }
    }

    public void setPerformanceManager(PerformanceManager performanceManager) {
        this.performanceManager = performanceManager;
    }
}
