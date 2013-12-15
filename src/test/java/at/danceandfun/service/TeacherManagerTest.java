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
import at.danceandfun.entity.Teacher;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TeacherManagerTest {

    private static Logger logger = Logger.getLogger(TeacherManagerImpl.class);

    @Autowired
    private TeacherManager teacherManager;
    @Autowired
    private CourseManager courseManager;

    @SuppressWarnings("unchecked")
    private DaoBase<Teacher> dao = (DaoBase<Teacher>) mock(DaoBase.class);

    @Test
    public void searchForTeachers() {

        Course actualCourse = courseManager.get(2);
        List<Teacher> possibleTeachers = teacherManager.searchForTeachers(
                actualCourse, "Cari");

        assertThat(possibleTeachers.get(0), is(notNullValue()));
    }

}
