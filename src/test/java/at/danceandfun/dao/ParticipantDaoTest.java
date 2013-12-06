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

import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.CourseParticipantID;
import at.danceandfun.entity.Participant;
import at.danceandfun.enumeration.Duration;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParticipantDaoTest {

    @Autowired
    private DaoBaseImpl<Participant> participantDao;

    @Autowired
    private DaoBaseImpl<Course> courseDao;

    public static Participant getValidParticipant() {
        Participant participant = new Participant();
        participant.setAddress(AddressDaoTest.getValidAddress());
        participant.setBirthday(new LocalDate().minusYears(10));
        participant.setFirstname("first");
        participant.setLastname("last");
        participant.setTelephone("123456789");
        participant.setEmail("mail@mail.com");
        participant.setEmergencyNumber("emergency");
        participant.setContactPerson("contact person");
        return participant;
    }

    @Test
    public void testSave() {
        participantDao.save(getValidParticipant());
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

    @Test
    public void testCourseParticipantRelation() {
        Course course = courseDao.get(1);
        if (course != null) {
            Participant participant = getValidParticipant();
            CourseParticipant courseParticipant = new CourseParticipant();
            courseParticipant.setDuration(Duration.YEAR);
            CourseParticipantID key = new CourseParticipantID();
            key.setParticipant(participant);
            key.setCourse(course);
            courseParticipant.setKey(key);
            List<CourseParticipant> courseParticipants = new ArrayList<CourseParticipant>();
            courseParticipants.add(courseParticipant);
            participant.setCourseParticipants(courseParticipants);
            participantDao.save(participant);
        } else {
            fail("database empty");
        }
    }
}
