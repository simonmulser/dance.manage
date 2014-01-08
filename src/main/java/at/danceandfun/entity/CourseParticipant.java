package at.danceandfun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import at.danceandfun.enumeration.Duration;

@Entity
@Table(name = "COURSE_PARTICIPANT")
public class CourseParticipant extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CP_ID")
    @GeneratedValue
    private Integer cpid;

    @JoinColumn(name = "C_ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    @JoinColumn(name = "P_ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Participant participant;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "ENABLED")
    private boolean enabled;

    public CourseParticipant() {
    }

    public Integer getCpid() {
        return cpid;
    }

    public void setCpid(Integer cpid) {
        this.cpid = cpid;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Duration getDuration() {
        return Duration.parse(this.duration);
    }

    public void setDuration(Duration duration) {
        this.duration = duration.getValue();
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
        result = prime * result + ((cpid == null) ? 0 : cpid.hashCode());
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
        if (cpid == null) {
            if (other.cpid != null) {
                return false;
            }
        } else if (!cpid.equals(other.cpid)) {
            return false;
        }
        return true;
    }

}
