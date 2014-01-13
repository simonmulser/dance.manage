package at.danceandfun.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "PERFORMANCE")
public class Performance extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = -4142311801625221575L;

    @Id
    @Column(name = "PER_ID")
    @GeneratedValue
    private Integer perid;

    @Column(name = "DATETIME")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private DateTime dateTime;

    @ElementCollection
    private List<Integer> courseIds;

    @Column(name = "ENABLED")
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "A_ID")
    private Address address;

    // @ManyToMany(cascade = { CascadeType.ALL })
    // @JoinTable(name = "COURSE_PERFORMANCE", joinColumns = { @JoinColumn(name
    // = "PER_ID") }, inverseJoinColumns = { @JoinColumn(name = "C_ID") })
    // private List<Course> courses = new ArrayList<Course>();

    @Transient
    private List<Course> courses = new ArrayList<Course>();

    @ManyToMany(mappedBy = "performances")
    private List<PerformancePlan> performancePlans = new ArrayList<PerformancePlan>();

    // TODO NiceToHave mapping with person/participant for ticket selling

    public Performance() {
    }

    public Integer getPerid() {
        return perid;
    }

    public void setPerid(Integer perid) {
        this.perid = perid;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Integer> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Integer> courseIds) {
        this.courseIds = courseIds;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<PerformancePlan> getPerformancePlans() {
        return performancePlans;
    }

    public void setPerformancePlans(List<PerformancePlan> performancePlans) {
        this.performancePlans = performancePlans;
    }

}
