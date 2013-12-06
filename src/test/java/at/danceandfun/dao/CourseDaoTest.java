package at.danceandfun.dao;

import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Style;
import at.danceandfun.enumeration.AgeGroup;
import at.danceandfun.enumeration.CourseDuration;
import at.danceandfun.enumeration.CourseLevel;
import at.danceandfun.enumeration.SpectatorAmount;
import at.danceandfun.enumeration.WeekDay;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseDaoTest {

    @Autowired
    private DaoBase<Course> courseDao;

    @Autowired
    private DaoBase<Style> styleDao;

    public static Course getValidCourse() {
        Course course = new Course();
        course.setAgeGroup(AgeGroup.BIG);
        course.setAmountPerformances(5);
        course.setWeekday(WeekDay.MONDAY);
        course.setDuration(CourseDuration.HUNDRED);
        course.setEstimatedSpectators(SpectatorAmount.HIGH);
        course.setLevel(CourseLevel.ADVANCED);
        course.setName("course");
        course.setAddress(AddressDaoTest.getValidAddress());
        course.setStyle(StyleDaoTest.getValidStyle());
        course.setSemesterPrice(80.0);
        course.setYearPrice(140.0);
        course.setYear(2012);
        course.setTime(new LocalTime());
        return course;
    }

    @Test
    public void testSaveCourse() {
        courseDao.update(getValidCourse());
    }
}
