package at.danceandfun.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class AbsenceID implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Appointment appointment;
    private Participant participant;

    public AbsenceID() {
    }

    public AbsenceID(Appointment appointment, Participant participant) {
        this.appointment = appointment;
        this.participant = participant;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((appointment == null) ? 0 : appointment.hashCode());
        result = prime * result
                + ((participant == null) ? 0 : participant.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbsenceID other = (AbsenceID) obj;
        if (appointment == null) {
            if (other.appointment != null) {
                return false;
            }
        } else if (!appointment.equals(other.appointment)) {
            return false;
        }
        if (participant == null) {
            if (other.participant != null) {
                return false;
            }
        } else if (!participant.equals(other.participant)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "participant:" + participant + " appointment:" + appointment;
    }
}
