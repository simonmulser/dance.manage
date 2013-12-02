package at.danceandfun.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class CourseParticipantID implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Course course;
    private Participant participant;

    @ManyToOne(fetch = FetchType.LAZY)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        result = prime * result + ((course == null) ? 0 : course.hashCode());
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
        CourseParticipantID other = (CourseParticipantID) obj;
        if (course == null) {
            if (other.course != null) {
                return false;
            }
        } else if (!course.equals(other.course)) {
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

}
