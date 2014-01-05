package at.danceandfun.util;

import java.util.List;

import at.danceandfun.entity.Appointment;

public class AppointmentsWrapper {

    private List<Appointment> appointments;

    public AppointmentsWrapper() {
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
