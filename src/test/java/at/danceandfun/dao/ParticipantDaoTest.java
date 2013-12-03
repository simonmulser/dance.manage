package at.danceandfun.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Address;
import at.danceandfun.entity.Participant;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParticipantDaoTest {

    @Autowired
    private DaoBaseImpl<Participant> participantDao;

    /*
     * Testing generic methods. only one time necessary. don't repeat in other
     * entities.
     */

    @Test
    public void testSave() {
        Participant participant = new Participant();
        Address address = new Address();
        address.setCity("city");
        address.setStreet("street");
        address.setZip(12);
        address.setNumber(12);
        participant.setAddress(address);
        participant.setBirthday(new LocalDate());
        participant.setFirstname("first");
        participant.setLastname("last");
        participant.setTelephone("123456789");
        participant.setEmail("mail@mail.com");
        participant.setEmergencyNumber("emergency");
        participant.setContactPerson("contact person");
        participantDao.save(participant);
    }

    @Test
    public void testUpdate() {
        Participant participant = participantDao.get(1);
        if (participant != null) {
            participantDao.update(participant);
        } else {
            fail("database is empty");
        }
    }

    @Test
    public void testGetSiblings() {
        Participant participant = participantDao.get(3);
        if (participant != null) {
            assertThat(participant.getSiblings().size(), is(2));
        } else {
            fail("database is empty");
        }
    }

    @Test
    public void testGetSiblings_forOtherSiblingShouldBeSameNumberOfSiblings() {
        Participant participant = participantDao.get(4);
        if (participant != null) {
            assertThat(participant.getSiblings().size(), is(2));
        } else {
            fail("database is empty");
        }
    }

    @Test
    public void testListByCriteria() {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);
        assertThat(participantDao.getListByCriteria(criteria).isEmpty(),
                is(false));
    }

    @Test
    public void testListByCriteriaWithOffsetAndSize() {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);
        assertThat(participantDao.getListByCriteria(criteria, 0, 1).isEmpty(),
                is(false));
    }

    @Test
    public void testListByCriterions() {
        List<Criterion> criterions = new ArrayList<Criterion>();

        criterions.add(Restrictions.eq("enabled", true));
        assertThat(participantDao.getListByCriterions(criterions).isEmpty(),
                is(false));

    }

}
