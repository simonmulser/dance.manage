package at.danceandfun.dao;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Absence;
import at.danceandfun.entity.AbsenceID;
import at.danceandfun.entity.Appointment;
import at.danceandfun.entity.Participant;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AbsenceDaoTest {

    @Autowired
    private DaoBaseImpl<Absence> absenceDao;

    public static Absence getValidAbsence() {
        Absence absence = new Absence();
        absence.setReason("reason");
        AbsenceID absenceID = new AbsenceID();
        // setting ID's for participant and appointment to avoid nullpointer
        Participant participant = ParticipantDaoTest.getValidParticipant();
        participant.setPid(1);
        Appointment appointment = AppointmentDaoTest.getValidAppointment();
        appointment.setApid(1);
        absenceID.setParticipant(participant);
        absenceID.setAppointment(appointment);
        absence.setKey(absenceID);
        return absence;
    }

    @Test
    public void testSave() {
        absenceDao.persist(getValidAbsence());
    }

    @Test
    public void testUpdate() {
        Participant participant = ParticipantDaoTest.getValidParticipant();
        participant.setPid(1);
        Appointment appointment = AppointmentDaoTest.getValidAppointment();
        appointment.setApid(1);
        AbsenceID absenceID = new AbsenceID(appointment, participant);
        Absence absence = absenceDao.get(absenceID);
        if (absence != null) {
            absenceDao.merge(absence);
        } else {
            fail("empty database");
        }

        absenceDao.merge(getValidAbsence());
    }
}
