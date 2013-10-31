package at.danceandfun.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Participant;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParticipantDaoTest {

    @Autowired
    private ParticipantDao participantDAO;

    /*
     * Testing generic methods. only one time necessary. don't repeat in other
     * entities.
     */
    @Test
    public void testGetList() {
        assertThat(participantDAO.getList().isEmpty(), is(false));
    }

    @Test
    public void testSave() {
        Participant participant = new Participant();
        participantDAO.save(participant);
    }

    @Test
    @Ignore
    public void testUpdate() {
        Participant participant = participantDAO.get(1);
        if (participant != null) {
            participantDAO.update(participant);
        } else {
            fail("database is empty");
        }
    }

    @Test
    public void testGet() {
        Participant participant = participantDAO.get(1);
        if (participant != null) {
            assertThat(participant.getId(), is(1));
        } else {
            fail("database is empty");
        }
    }

    @Test
    public void testListByCriteria() {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);
        assertThat(participantDAO.getListByCriteria(criteria).isEmpty(),
                is(false));
    }

    @Test
    public void testListByCriteriaWithOffsetAndSize() {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);
        assertThat(participantDAO.getListByCriteria(criteria, 0, 1).isEmpty(),
                is(false));
    }
}
