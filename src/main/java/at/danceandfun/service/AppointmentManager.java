package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Appointment;

public interface AppointmentManager extends ManagerBase<Appointment> {

    public List<Appointment> getEnabledAppointmentsForCourseId(int courseId);
}
