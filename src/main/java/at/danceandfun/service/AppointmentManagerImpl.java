package at.danceandfun.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Appointment;

@Service
public class AppointmentManagerImpl extends ManagerBaseImpl<Appointment>
        implements AppointmentManager {

    private static Logger logger = Logger
            .getLogger(AppointmentManagerImpl.class);

    @Autowired
    public void setDao(DaoBaseImpl<Appointment> appointmentDao) {
        setMainDao(appointmentDao);
    }
}
