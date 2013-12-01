package at.danceandfun.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import at.danceandfun.role.RoleTeacher;

@Entity
@Table(name = "TEACHER")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Teacher extends Person {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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
