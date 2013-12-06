package at.danceandfun.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.DaoBase;
import at.danceandfun.entity.Course;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Teacher;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseManagerTest {

    private static Logger logger = Logger.getLogger(CourseManagerImpl.class);

    @Autowired
    private CourseManager courseManager;
    @Autowired
    private ParticipantManager participantManager;
    @Autowired
    private TeacherManager teacherManager;

    @SuppressWarnings("unchecked")
    private DaoBase<Course> dao = (DaoBase<Course>) mock(DaoBase.class);

    @SuppressWarnings("unchecked")
    @Test
    public void searchForParticipantCourses() {
        Participant actualParticipant = participantManager.get(3);
        List<Course> possibleCourses = courseManager.searchForCourses(
                actualParticipant, "Ballett");

        assertThat(possibleCourses.get(0), is(notNullValue()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void searchForTeacherCourses() {
        Teacher actualTeacher = teacherManager.get(7);
        List<Course> possibleCourses = courseManager.searchForCourses(
                actualTeacher, "Ballett");

        assertThat(possibleCourses.get(0), is(notNullValue()));
    }
}
