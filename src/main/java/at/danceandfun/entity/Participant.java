package at.danceandfun.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import at.danceandfun.role.RoleParticipant;

@Entity
@Table(name = "PARTICIPANT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Participant extends Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8277820405941269587L;

    @Column(name = "EMERGENCYNUMBER")
    @NotEmpty
    private String emergencyNumber;

    @Column(name = "CONTACTPERSON")
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zäöüÄÖÜ]*$", message = "darf nur aus Buchstaben bestehen")
    private String contactPerson;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "COURSE_PARTICIPANT", joinColumns = { @JoinColumn(name = "P_ID") }, inverseJoinColumns = { @JoinColumn(name = "C_ID") })
    private List<Course> courses = new ArrayList<Course>();

    // TODO NiceToHave mapping with performance for ticket selling

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "PARTICIPANT_SIBLING", joinColumns = { @JoinColumn(name = "P_ID") }, inverseJoinColumns = { @JoinColumn(name = "P_ID_REVERSE") })
    private Set<Participant> siblings = new HashSet<Participant>();

    @ManyToMany(mappedBy = "siblings")
    private Set<Participant> siblingsReverse = new HashSet<Participant>();

    private String tempSiblings;

    private String tempCourses;

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @JsonIgnore
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
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

    @JsonIgnore
    public Set<Participant> getReverseSiblings() {
        return this.siblingsReverse;
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
