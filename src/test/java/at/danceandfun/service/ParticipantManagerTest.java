package at.danceandfun.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void testGetParticipantsByNumberOfCourses_emptyAnswer() {
        participantManager.setDao(dao);
        when(dao.getQueryResults(anyString())).thenReturn(
                new ArrayList<Object>().iterator());

        Map<Integer, List<Participant>> map = participantManager
                .getParticipantsByNumberOfCourses();
        assertThat(map.size(), is(0));
    }

    @Test
    public void testGetParticipantsByNumberOfCourses_withAnswer() {
        participantManager.setDao(dao);
        Object[] test = new Object[2];
        Participant participant = new Participant();
        test[0] = participant;
        test[1] = new Long(2);
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(test);

        when(dao.getQueryResults(anyString())).thenReturn(list.iterator());

        Map<Integer, List<Participant>> map = participantManager
                .getParticipantsByNumberOfCourses();
        assertThat(map.get(2).get(0), is(participant));
    }

}
