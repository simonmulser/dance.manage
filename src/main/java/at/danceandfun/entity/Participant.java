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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import at.danceandfun.role.RoleAdmin;
import at.danceandfun.role.RoleUser;

@Entity
@Table(name = "PARTICIPANT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Participant extends Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8277820405941269587L;

    @Column(name = "EMERGENCYNUMBER")
    private String emergencyNumber;

    @Column(name = "CONTACTPERSON")
    private String contactPerson;

    @ManyToMany(mappedBy = "participants")
    private List<Course> courses;

    // TODO NiceToHave mapping with performance for ticket selling

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "PARTICIPANT_SIBLING", joinColumns = { @JoinColumn(name = "P_ID") }, inverseJoinColumns = { @JoinColumn(name = "P_ID_REVERSE") })
    private Set<Participant> siblings = new HashSet<Participant>();

    @ManyToMany(mappedBy = "siblings")
    private Set<Participant> siblingsReverse = new HashSet<Participant>();

    private String tempSiblings;

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

    public Set<Participant> getReverseSiblings() {
        return this.siblingsReverse;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new RoleUser());
        auth.add(new RoleAdmin());
        return auth;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }
}
