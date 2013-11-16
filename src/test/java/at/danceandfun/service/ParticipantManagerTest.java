package at.danceandfun.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParticipantManagerTest {

    @Autowired
    private ParticipantManager participantManager;

    @Test
    public void testGetParticipantsByNumberOfCourses() {
        participantManager.getParticipantsByNumberOfCourses();
    }

}
