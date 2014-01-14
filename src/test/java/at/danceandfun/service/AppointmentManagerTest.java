package at.danceandfun.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AppointmentManagerTest {

    private static Logger logger = Logger
            .getLogger(AppointmentManagerTest.class);

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private AppointmentManagerImpl appointmentManager;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetEnabledAppointmentsForCourseId() {
        assertThat(appointmentManager.getEnabledAppointmentsForCourseId(1)
                .size(), is(9));
    }
}
