package at.danceandfun.dao;

import java.sql.Time;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Course;
import at.danceandfun.enumeration.AgeGroup;
import at.danceandfun.enumeration.CourseLevel;
import at.danceandfun.enumeration.SpectatorAmount;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseDaoTest {

    @Autowired
    private DaoBase<Course> courseDao;

    public static Course getValidCourse() {
        Course course = new Course();
        course.setAgeGroup(AgeGroup.BIG);
        course.setAmountPerformances(5);
        course.setDuration(120);
        course.setEstimatedSpectators(SpectatorAmount.HIGH);
        course.setLevel(CourseLevel.ADVANCED);
        course.setName("course");
        course.setSemesterPrice(80.0);
        course.setYearPrice(140.0);
        // course.setYear(Years.years(2012));
        course.setTime(new Time(0));
        return course;
    }

    @Test
    public void testSaveCourse() {
        courseDao.save(getValidCourse());
    }
}
