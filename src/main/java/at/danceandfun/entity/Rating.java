package at.danceandfun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RATING")
public class Rating {

    @Id
    @Column(name = "R_ID")
    @GeneratedValue
    private Integer rid;

    @Column(name = "COURSE_RATING")
    private Integer courseRating;

    @Column(name = "TEACHER_RATING")
    private Integer teacherRating;

    @Column(name = "SERVICE_RATING")
    private Integer serviceRating;

    @Column(name = "PROCRITIQUE")
    private String proCritique;

    @Column(name = "CONTRACRITIQUE")
    private String contraCritique;

    @Column(name = "ANSWER")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "C_ID")
    private Course course;

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

}
