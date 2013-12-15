package at.danceandfun.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ABSENCE")
@AssociationOverrides({
        @AssociationOverride(name = "key.appointment", joinColumns = @JoinColumn(name = "AP_ID")),
        @AssociationOverride(name = "key.participant", joinColumns = @JoinColumn(name = "P_ID")) })
public class Absence extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private AbsenceID key = new AbsenceID();

    @Column(name = "REASON")
    private String reason;

    @Column(name = "ENABLED")
    private boolean enabled;

    public Absence(Participant participant, Appointment appointment,
            String reason) {
        key.setParticipant(participant);
        key.setAppointment(appointment);
        this.reason = reason;
        enabled = true;
    }

    public Absence() {
    }

    @EmbeddedId
    public AbsenceID getKey() {
        return key;
    }

    public void setKey(AbsenceID key) {
        this.key = key;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((reason == null) ? 0 : reason.hashCode());
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
        Absence other = (Absence) obj;
        if (enabled != other.enabled) {
            return false;
        }
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        if (reason == null) {
            if (other.reason != null) {
                return false;
            }
        } else if (!reason.equals(other.reason)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "absence key(" + key.toString() + ") reason:" + reason
                + " enabled:" + enabled;
    }

}
