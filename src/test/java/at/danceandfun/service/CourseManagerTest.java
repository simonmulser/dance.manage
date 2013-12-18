package at.danceandfun.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.CourseDaoTest;
import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Course;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Teacher;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseManagerTest {

    private static Logger logger = Logger.getLogger(CourseManagerTest.class);

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private ParticipantManager participantManager;
    @Autowired
    private TeacherManager teacherManager;

    @Autowired
    private StyleManager styleManager;

    @SuppressWarnings("unchecked")
    private DaoBaseImpl<Course> dao = (DaoBaseImpl<Course>) mock(DaoBaseImpl.class);

    @Autowired
    private DaoBaseImpl<Course> courseDao;

    @Test
    public void searchForParticipantCourses() {
        Participant actualParticipant = participantManager.get(3);
        List<Course> possibleCourses = courseManager.searchForCourses(
                actualParticipant, "Ballett");

        assertThat(possibleCourses.get(0), is(notNullValue()));
    }

    @Test
    public void searchForTeacherCourses() {
        Teacher actualTeacher = teacherManager.get(7);
        List<Course> possibleCourses = courseManager.searchForCourses(
                actualTeacher, "Ballett");

        assertThat(possibleCourses.get(0), is(notNullValue()));
    }

    @Test
    public void testSaveWithSlugMocktio() {
        ((CourseManagerImpl) courseManager).setDao(dao);
        courseManager.update(CourseDaoTest.getValidCourse());

        verify(dao).persist(any(Course.class));
        ((CourseManagerImpl) courseManager).setDao(courseDao);
    }
}
