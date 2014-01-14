package at.danceandfun.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Appointment;
import at.danceandfun.entity.Course;

@Service
public class AppointmentManagerImpl extends ManagerBaseImpl<Appointment>
        implements AppointmentManager {

    private static Logger logger = Logger
            .getLogger(AppointmentManagerImpl.class);

    private CourseManager courseManager;

    @Autowired
    public void setCourseManager(CourseManager courseManager) {
        this.courseManager = courseManager;
    }

    @Autowired
    public void setDao(DaoBaseImpl<Appointment> appointmentDao) {
        setMainDao(appointmentDao);
    }

    @Override
    public List<Appointment> getEnabledAppointmentsForCourseId(int courseId) {
        Course course = courseManager.get(courseId);
        logger.info("get enabled appointments for course " + course);

        DetachedCriteria criteria = DetachedCriteria
                .forClass(Appointment.class);
        criteria.add(Restrictions.eq("course", course));

        return getEnabledListWithCriteria(criteria);
    }
}
