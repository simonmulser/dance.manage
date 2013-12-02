package at.danceandfun.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import at.danceandfun.enumeration.Duration;

@Entity
@Table(name = "COURSE_PARTICIPANT")
@AssociationOverrides({
        @AssociationOverride(name = "key.participant", joinColumns = @JoinColumn(name = "P_ID")),
        @AssociationOverride(name = "key.course", joinColumns = @JoinColumn(name = "C_ID")) })
public class CourseParticipant extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private CourseParticipantID key = new CourseParticipantID();

    @Column(name = "DURATION")
    private Duration duration;

    @Column(name = "ENABLED")
    private boolean enabled;

    public CourseParticipant() {
    }

    @EmbeddedId
    public CourseParticipantID getKey() {
        return key;
    }

    public void setKey(CourseParticipantID key) {
        this.key = key;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
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
        result = prime * result + ((key == null) ? 0 : key.hashCode());
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
        CourseParticipant other = (CourseParticipant) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

}
