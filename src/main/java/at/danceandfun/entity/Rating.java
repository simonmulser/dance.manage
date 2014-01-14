package at.danceandfun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import at.danceandfun.util.PatternConstants;

@Entity
@Table(name = "RATING")
public class Rating extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "R_ID")
    @GeneratedValue
    private Integer rid;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "COURSE_RATING")
    @NotNull
    private Integer courseRating;

    @Column(name = "TEACHER_RATING")
    @NotNull
    private Integer teacherRating;

    @Column(name = "SERVICE_RATING")
    @NotNull
    private Integer serviceRating;

    @Column(name = "PROCRITIQUE")
    @Pattern(regexp = PatternConstants.MESSAGE_PATTERN, message = "{pattern.characters.message}")
    private String proCritique;

    @Column(name = "CONTRACRITIQUE")
    @Pattern(regexp = PatternConstants.MESSAGE_PATTERN, message = "{pattern.characters.message}")
    private String contraCritique;

    @Column(name = "ANSWER")
    @Pattern(regexp = PatternConstants.MESSAGE_PATTERN, message = "{pattern.characters.message}")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "C_ID")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "P_ID")
    private Participant participant;

    public Rating() {
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getCourseRating() {
        return courseRating;
    }

    public void setCourseRating(Integer courseRating) {
        this.courseRating = courseRating;
    }

    public Integer getTeacherRating() {
        return teacherRating;
    }

    public void setTeacherRating(Integer teacherRating) {
        this.teacherRating = teacherRating;
    }

    public Integer getServiceRating() {
        return serviceRating;
    }

    public void setServiceRating(Integer serviceRating) {
        this.serviceRating = serviceRating;
    }

    public String getProCritique() {
        return proCritique;
    }

    public void setProCritique(String proCritique) {
        this.proCritique = proCritique;
    }

    public String getContraCritique() {
        return contraCritique;
    }

    public void setContraCritique(String contraCritique) {
        this.contraCritique = contraCritique;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
