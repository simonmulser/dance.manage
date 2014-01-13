package at.danceandfun.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.DaoBase;
import at.danceandfun.entity.CourseParticipant;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseParticipantManagerTest {

    private static Logger logger = Logger
            .getLogger(CourseParticipantManagerImpl.class);

    @Autowired
    private CourseParticipantManager courseParticipantManager;

    @SuppressWarnings("unchecked")
    private DaoBase<CourseParticipant> dao = (DaoBase<CourseParticipant>) mock(DaoBase.class);

    @Test
    public void testGetCourseCountByParticipant() {
        assertThat(courseParticipantManager.getCourseCountByParticipant(1, 1),
                is(notNullValue()));
    }

}
