package at.danceandfun.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private Map<Integer, Performance> performancePlanMap;
    private PerformancePlan performancePlan;
    private List<PerformancePlan> performancePlanList;
    private LocalDate dateTime;
    private int currentPlanId;
    private boolean balletRestriction = true;
    private boolean twoBreaksRestriction = true;
    private boolean advancedAtEndRestriction = true;
    private boolean balancedAmountOfSpectators = true;
    private boolean balancedAgeGroup = true;
    private boolean multipleGroupsSamePerformance = true;
    private boolean sibsSamePerformance = true;
    private boolean isSavedPlan = false;

    private boolean editTrue = false;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listPerformances(ModelMap map) {
        logger.debug("LIST performances with id " + performance.getPerid());

        if (!editTrue) {
            performancePlan = new PerformancePlan();
        }

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
        map.addAttribute("isSavedPlan", isSavedPlan);
        map.addAttribute("performancePlan", performancePlan);
        map.addAttribute("performancePlanList",
                performancePlanManager.getEnabledList());
        map.addAttribute("dateTime", dateTime);
        map.addAttribute("currentPlanId", currentPlanId);

        editTrue = false;
        return "admin/performanceView";
    }

    @RequestMapping(value = "/jumpToPlan/{plan}", method = RequestMethod.GET)
    public String jumpToPlan(@PathVariable("plan") Integer plan, ModelMap map) {

        return "redirect:/admin/performance#plan" + plan;
    }

    @RequestMapping(value = "/build", method = RequestMethod.POST)
    public String buildPerformance(
            ModelMap map,
            HttpServletRequest request,
            @ModelAttribute(value = "performancePlan") @Valid PerformancePlan plan,
            BindingResult result, RedirectAttributes redirectAttributes) {
        logger.debug("BUILD performance");

        setCheckedRestrictions(request);

        if (result.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute(
                            "org.springframework.validation.BindingResult.performancePlan",
                            result);
            redirectAttributes.addFlashAttribute("performancePlan", plan);
            this.performancePlan = plan;
            editTrue = true;
            return "redirect:/admin/performance";

        }
        performancePlanMap = new HashMap<Integer, Performance>();

        List<Course> courses = courseManager.getEnabledList();
        List<Participant> participantList = participantManager.getEnabledList();

        for (Course course : courses) {
            if (!course.isInPerformance()) {
                courses.remove(course);
            }
        }

        dateTime = plan.getDateTime();

        Collections.shuffle(courses);

        GenerateSatSolution sat = new GenerateSatSolution();

        int countTries = 0;
        while (true) {
            try {
                if (countTries == 2) {
                    sibsSamePerformance = false;
                    multipleGroupsSamePerformance = false;
                }
                performancePlanMap = sat.generatePerformance(courses,
                        participantList, balletRestriction,
                        twoBreaksRestriction, advancedAtEndRestriction,
                        balancedAmountOfSpectators, balancedAgeGroup,
                        multipleGroupsSamePerformance, sibsSamePerformance);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SatException s) {
                System.out.println(s.getMessage());
                if (s.getMessage().equals("Execution too long")) {
                    countTries++;
                }

                Collections.shuffle(courses);
                continue;
            }
        }

        SatValidator validator = new SatValidator(performancePlanMap,
                participantList);
        performancePlanMap = validator.validatePerformancePlan();

        tempPerformance1 = performancePlanMap.get(1);
        tempPerformance2 = performancePlanMap.get(2);
        tempPerformance3 = performancePlanMap.get(3);

        performance = new Performance();

        isSavedPlan = false;

        return "redirect:/admin/performance";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public String validatePerformance(
            ModelMap map,
            HttpServletRequest request,
            @ModelAttribute(value = "performancePlan") @Valid PerformancePlan plan,
            BindingResult result, RedirectAttributes redirectAttributes) {
        logger.debug("VALDIATE performancePlan");
        List<Participant> participantList = participantManager.getEnabledList();

        performancePlanMap.put(1, tempPerformance1);
        performancePlanMap.put(2, tempPerformance2);
        performancePlanMap.put(3, tempPerformance3);

        SatValidator validator = new SatValidator(performancePlanMap,
                participantList);
        performancePlanMap = validator.validatePerformancePlan();

        tempPerformance1 = performancePlanMap.get(1);
        tempPerformance2 = performancePlanMap.get(2);
        tempPerformance3 = performancePlanMap.get(3);

        return "redirect:/admin/performance";
    }

    @RequestMapping(value = "/save")
    public String savePerformancePlan() {
        logger.debug("SAVE the performanceplan");

        for (Course c : tempPerformance1.getCourses()) {
            System.out.println(c.getName());
        }

        tempPerformance1.setEnabled(true);
        tempPerformance2.setEnabled(true);
        tempPerformance3.setEnabled(true);

        List<Performance> performances = new ArrayList<Performance>();
        performances.add(tempPerformance1);
        performances.add(tempPerformance2);
        performances.add(tempPerformance3);

        performancePlan.setPerformances(performances);

        performancePlan.setDateTime(dateTime);
        performancePlan.setEnabled(true);

        performancePlanManager.persist(performancePlan);

        isSavedPlan = true;

        currentPlanId = performancePlan.getPlanid();

        performance = new Performance();

        return "redirect:/admin/performance";
    }

    @RequestMapping(value = "/show/{planid}")
    public String showPerformancePlan(@PathVariable("planid") Integer planid,
            ModelMap map) {
        logger.debug("Show Performanceplan with id " + planid);

        PerformancePlan plan = performancePlanManager.get(planid);
        List<Participant> participantList = participantManager.getEnabledList();

        List<Course> fetchedCourses = courseManager.getEnabledList();
        List<Performance> fetchedPerformances = plan.getPerformances();

        if (fetchedPerformances.size() != 3) {
            return "redirect:/admin/performance";
        }

        for (Performance currentPerformance : fetchedPerformances) {
            List<Course> courseList = new ArrayList<Course>();
            for (Integer currentID : currentPerformance.getCourseIds()) {
                for (Course currrentCourse : fetchedCourses) {
                    if (currrentCourse.getCid() == currentID) {
                        courseList.add(currrentCourse);
                        continue;
                    }
                }
            }
            currentPerformance.setCourses(courseList);
        }

        tempPerformance1 = fetchedPerformances.get(0);
        tempPerformance2 = fetchedPerformances.get(1);
        tempPerformance3 = fetchedPerformances.get(2);

        performancePlanMap = new HashMap<Integer, Performance>();

        performancePlanMap.put(1, tempPerformance1);
        performancePlanMap.put(2, tempPerformance2);
        performancePlanMap.put(3, tempPerformance3);

        SatValidator validator = new SatValidator(performancePlanMap,
                participantList);
        performancePlanMap = validator.validatePerformancePlan();

        tempPerformance1 = performancePlanMap.get(1);
        tempPerformance2 = performancePlanMap.get(2);
        tempPerformance3 = performancePlanMap.get(3);

        dateTime = plan.getDateTime();

        isSavedPlan = true;

        currentPlanId = planid;

        performance = new Performance();

        return "redirect:/admin/performance";
    }

    @RequestMapping(value = "/delete/{planid}")
    public String deletePerformancePlan(@PathVariable("planid") Integer planid) {
        logger.debug("Delete Performanceplan with id " + planid);

        PerformancePlan plan = performancePlanManager.get(planid);
        plan.setEnabled(false);

        performancePlanManager.merge(plan);

        return "redirect:/admin/performance";
    }

    @RequestMapping(value = "/viewPerformancePdf/{planid}", method = RequestMethod.GET)
    public ModelAndView viewPerformancePdf(
            @PathVariable("planid") Integer planid) {
        logger.debug("Creating performance pdf");

        HashMap<String, Object> map = new HashMap<String, Object>(1);
        PerformancePlan plan = performancePlanManager.get(planid);
        List<Participant> participantList = participantManager.getEnabledList();

        List<Course> fetchedCourses = courseManager.getEnabledList();
        List<Performance> fetchedPerformances = plan.getPerformances();

        for (Performance currentPerformance : fetchedPerformances) {
            List<Course> courseList = new ArrayList<Course>();
            for (Integer currentID : currentPerformance.getCourseIds()) {
                for (Course currrentCourse : fetchedCourses) {
                    if (currrentCourse.getCid() == currentID) {
                        courseList.add(currrentCourse);
                        continue;
                    }
                }
            }
            currentPerformance.setCourses(courseList);
        }

        tempPerformance1 = fetchedPerformances.get(0);
        tempPerformance2 = fetchedPerformances.get(1);
        tempPerformance3 = fetchedPerformances.get(2);

        performancePlanMap = new HashMap<Integer, Performance>();

        performancePlanMap.put(1, tempPerformance1);
        performancePlanMap.put(2, tempPerformance2);
        performancePlanMap.put(3, tempPerformance3);

        SatValidator validator = new SatValidator(performancePlanMap,
                participantList);
        performancePlanMap = validator.validatePerformancePlan();

        tempPerformance1 = performancePlanMap.get(1);
        tempPerformance2 = performancePlanMap.get(2);
        tempPerformance3 = performancePlanMap.get(3);

        isSavedPlan = true;

        map.put("performanceList1", tempPerformance1.getCourses());
        map.put("performanceList2", tempPerformance2.getCourses());
        map.put("performanceList3", tempPerformance3.getCourses());

        return new ModelAndView("viewPerformancePdf", map);
    }

    @RequestMapping(value = "/swap", method = RequestMethod.POST)
    public String swapCourses(int performance, int posSource, int posTarget) {
        logger.info("Moving course in performance: " + performance);
        switch (performance) {
        case 1:
            tempPerformance1 = moveCourse(tempPerformance1, posSource,
                    posTarget);
            break;
        case 2:
            tempPerformance2 = moveCourse(tempPerformance2, posSource,
                    posTarget);
            break;
        case 3:
            tempPerformance3 = moveCourse(tempPerformance3, posSource,
                    posTarget);
            break;
        }

        return "redirect:/admin/performance";

    }

    private Performance moveCourse(Performance tempPerformance, int posSource,
            int posTarget) {
        logger.info("Moving item from position " + posSource + " to position "
                + posTarget);

        editTrue = true;

        List<Course> courses = tempPerformance.getCourses();
        List<Integer> courseIds = tempPerformance.getCourseIds();

        Course courseToMove = courses.get(posSource);
        logger.info("Switching object: " + courseToMove.getName());

        if (posSource < posTarget) {
            courses.add(posTarget + 1, courseToMove);
            courseIds.add(posTarget + 1, courseToMove.getCid());
            courses.remove(posSource);
            courseIds.remove(posSource);

        } else {
            courses.add(posTarget, courseToMove);
            courseIds.add(posTarget, courseToMove.getCid());
            courses.remove(posSource + 1);
            courseIds.remove(posSource);
        }

        logger.info("Element on new position: " + courses.get(posTarget));

        tempPerformance.setCourses(courses);
        tempPerformance.setCourseIds(courseIds);

        return tempPerformance;
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

    private List<Integer> setCourseIds(List<Course> courses) {
        List<Integer> courseID = new ArrayList<Integer>();

        for (Course currentCourse : courses) {
            courseID.add(currentCourse.getCid());
        }

        return courseID;
    }

    public void setPerformanceManager(PerformanceManager performanceManager) {
        this.performanceManager = performanceManager;
    }

}
