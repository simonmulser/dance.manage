package at.danceandfun.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.fail;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Appointment;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AppointmentDaoTest {

    @Autowired
    private DaoBaseImpl<Appointment> appointmentDao;

    public static Appointment getValidAppointment() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(new LocalDate());
        return appointment;
    }

    @Test
    public void testSave() {
        appointmentDao.persist(getValidAppointment());
    }

    @Test
    public void testUpdate() {
        Appointment appointment = appointmentDao.get(1);
        if (appointment != null) {
            appointmentDao.merge(appointment);
        } else {
            fail("database is empty");
        }
    }

    @Test
    public void testGetAbsence() {
        Appointment appointment = appointmentDao.get(1);
        if (appointment != null) {
            assertThat(appointment.getAbsences().size(), greaterThan(0));
        } else {
            fail("database is empty");
        }
    }
}
