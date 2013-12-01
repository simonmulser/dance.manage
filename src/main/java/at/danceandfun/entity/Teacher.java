package at.danceandfun.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import at.danceandfun.role.RoleTeacher;

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

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "TEMP_STYLES")
    private String tempStyles;

    @Column(name = "TEMP_COURSES")
    private String tempCourses;

    /*
     * @Column(name = "ENGAGEMENTDATE")
     * 
     * @Type(type =
     * "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime") private
     * DateTime engagementDate;
     */
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses = new ArrayList<Course>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "STYLE_TEACHER", joinColumns = { @JoinColumn(name = "P_ID") }, inverseJoinColumns = { @JoinColumn(name = "S_ID") })
    private List<Style> styles = new ArrayList<Style>();

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

    @JsonIgnore
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTempStyles() {
        return tempStyles;
    }

    public void setTempStyles(String tempStyles) {
        this.tempStyles = tempStyles;
    }

    public String getTempCourses() {
        return tempCourses;
    }

    public void setTempCourses(String tempCourses) {
        this.tempCourses = tempCourses;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new RoleTeacher());
        return auth;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
