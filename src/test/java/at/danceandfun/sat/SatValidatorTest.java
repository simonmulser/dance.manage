package at.danceandfun.sat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Performance;
import at.danceandfun.entity.PerformancePlan;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.ParticipantManager;
import at.danceandfun.service.PerformancePlanManager;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SatValidatorTest {

    private static Logger logger = Logger.getLogger(SatValidatorTest.class);

    @Autowired
    private PerformancePlanManager performancePlanManager;

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private ParticipantManager participantManager;

    private List<Course> fetchedCourses;
    private List<Performance> fetchedPerformances;
    private PerformancePlan plan;
    private Map<Integer, Performance> performancePlanMap;

    @Before
    public void setUp() {
        fetchedCourses = courseManager.getEnabledList();
        plan = performancePlanManager.get(2);

        fetchedPerformances = plan.getPerformances();

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

        performancePlanMap = new HashMap<Integer, Performance>();

        performancePlanMap.put(1, fetchedPerformances.get(0));
        performancePlanMap.put(2, fetchedPerformances.get(1));
        performancePlanMap.put(3, fetchedPerformances.get(2));

        SatValidator validator = new SatValidator(performancePlanMap,
                participantManager.getEnabledList());
        performancePlanMap = validator.validatePerformancePlan();
    }

    @After
    public void tearDown() {
        fetchedCourses = new ArrayList<Course>();
        fetchedPerformances = new ArrayList<Performance>();
        plan = new PerformancePlan();
    }

    @Test
    public void testValidationOfPerformanceVideoclipdancing2() {
        Performance currentPerformance = performancePlanMap.get(1);

        for (Course currentCourse : currentPerformance.getCourses()) {
            if (currentCourse.getName().equals("Videoclipdancing 2")) {
                assertTrue(currentCourse.getTwoBreaksRestriction());
                break;
            }
        }
    }

    @Test
    public void testValidationOfPerformanceBallett1MO() {
        Performance currentPerformance = performancePlanMap.get(1);

        for (Course currentCourse : currentPerformance.getCourses()) {
            if (currentCourse.getName().equals("Ballett 1 MO")) {
                assertFalse(currentCourse.getBalletRestriction());
                break;
            }
        }
    }
}
