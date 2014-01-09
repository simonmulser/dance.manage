package at.danceandfun.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.security.core.GrantedAuthority;

import at.danceandfun.role.RoleParticipant;

@Entity
@Table(name = "PARTICIPANT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Participant extends Person {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    @Column(name = "BIRTHDAY")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @NotNull
    @Past
    private LocalDate birthday;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    private List<CourseParticipant> courseParticipants = new ArrayList<CourseParticipant>();

    @OneToMany(mappedBy = "key.participant", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    private List<Absence> absences = new ArrayList<Absence>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "PARTICIPANT_SIBLING", joinColumns = { @JoinColumn(name = "P_ID") }, inverseJoinColumns = { @JoinColumn(name = "P_ID_REVERSE") })
    private Set<Participant> siblings = new HashSet<Participant>();

    @ManyToMany(mappedBy = "siblings")
    private Set<Participant> siblingsReverse = new HashSet<Participant>();

    @OneToMany(mappedBy = "participant")
    private List<Invoice> invoices = new ArrayList<Invoice>();

    @Transient
    private String tempSiblings;

    @Transient
    private String tempSiblingNames;

    @Transient
    private String tempCourses;

    @Transient
    private String tempCourseNames;

    @Transient
    private List<Course> actualCourses;

    public Participant() {
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @JsonIgnore
    public List<CourseParticipant> getCourseParticipants() {
        return courseParticipants;
    }

    public void setCourseParticipants(List<CourseParticipant> courseParticipants) {
        this.courseParticipants = courseParticipants;
    }

    public CourseParticipant getCourseById(Course course) {
        for (CourseParticipant cp : courseParticipants) {
            if (cp.getCourse().getCid() == course.getCid()) {
                return cp;
            }
        }
        return null;
    }

    @JsonIgnore
    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    @JsonIgnore
    public Set<Participant> getSiblings() {
        siblings.addAll(siblingsReverse);
        return siblings;
    }

    public void setSiblings(Set<Participant> siblings) {
        this.siblings = siblings;
    }

    public String getTempSiblings() {
        return this.tempSiblings;
    }

    public void setTempSiblings(String tempSiblings) {
        this.tempSiblings = tempSiblings;
    }

    public String getTempCourses() {
        return this.tempCourses;
    }

    public void setTempCourses(String tempCourses) {
        this.tempCourses = tempCourses;
    }

    public String getTempSiblingNames() {
        return tempSiblingNames;
    }

    public void setTempSiblingNames(String tempSiblingNames) {
        this.tempSiblingNames = tempSiblingNames;
    }

    public String getTempCourseNames() {
        return tempCourseNames;
    }

    public void setTempCourseNames(String tempCourseNames) {
        this.tempCourseNames = tempCourseNames;
    }

    public List<Course> getActualCourses() {
        return actualCourses;
    }

    public void setActualCourses(List<Course> actualCourses) {
        this.actualCourses = actualCourses;
    }

    @JsonIgnore
    public Set<Participant> getReverseSiblings() {
        return this.siblingsReverse;
    }

    @JsonIgnore
    public List<Invoice> getInvoices() {
        return invoices;
    }

    @Transient
    public String getIcon() {
        return "icon-female";
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public boolean hasParent() {
        if (this.parent == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new RoleParticipant());
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
