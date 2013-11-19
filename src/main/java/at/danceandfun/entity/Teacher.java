package at.danceandfun.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TEACHER")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Teacher extends Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3696904570909388688L;

    @Column(name = "SVNR")
    private String svnr;

    @Column(name = "SALARY")
    private Double salary;

    /*
     * @Column(name = "ENGAGEMENTDATE")
     * 
     * @Type(type =
     * "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime") private
     * DateTime engagementDate;
     */
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    @ManyToMany(mappedBy = "teachers")
    private List<Style> styles;

    public String getSvnr() {
        return svnr;
    }

    public void setSvnr(String svnr) {
        this.svnr = svnr;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /*
     * public DateTime getEngagementDate() { return engagementDate; }
     * 
     * public void setEngagementDate(DateTime engagementDate) {
     * this.engagementDate = engagementDate; }
     */

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

}
