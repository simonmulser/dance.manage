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

}
