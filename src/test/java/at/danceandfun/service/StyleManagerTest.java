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
import at.danceandfun.entity.Style;
import at.danceandfun.entity.Teacher;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StyleManagerTest {

    private static Logger logger = Logger.getLogger(StyleManagerImpl.class);

    @Autowired
    private CourseManager courseManager;
    @Autowired
    private StyleManager styleManager;
    @Autowired
    private TeacherManager teacherManager;

    @SuppressWarnings("unchecked")
    private DaoBase<Style> dao = (DaoBase<Style>) mock(DaoBase.class);

    @SuppressWarnings("unchecked")
    @Test
    public void searchForTeacherStyles() {
        Teacher actualTeacher = teacherManager.get(7); /* hat Ballett */
        List<Style> possibleStyles = styleManager.searchForStyles(
                actualTeacher, "Hip");

        assertThat(possibleStyles.get(0), is(notNullValue()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void searchForCourseStyles() {
        Course actualCourse = courseManager.get(25); /* Hip Hop */
        List<Style> possibleStyles = styleManager.searchForStyles(actualCourse,
                "Bal");

        assertThat(possibleStyles.get(0), is(notNullValue()));
    }
}
