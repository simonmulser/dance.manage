package at.danceandfun.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class CourseParticipantID implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Course course;
    private Participant participant;

    public CourseParticipantID() {
    }

    @ManyToOne
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne
    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

}
