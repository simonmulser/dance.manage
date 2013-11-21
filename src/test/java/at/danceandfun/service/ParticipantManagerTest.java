package at.danceandfun.service;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.DaoBase;
import at.danceandfun.entity.Participant;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParticipantManagerTest {

    @Autowired
    private ParticipantManager participantManager;

    @SuppressWarnings("unchecked")
    private DaoBase<Participant> dao = (DaoBase<Participant>) mock(DaoBase.class);

    @Test
    public void test() {
    }

}
