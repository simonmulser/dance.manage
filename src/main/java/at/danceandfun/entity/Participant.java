package at.danceandfun.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PARTICIPANT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Participant extends Person {

    @Column(name = "EMERGENCYNUMBER")
    private String emergencyNumber;

    @Column(name = "CONTACTPERSON")
    private String concatPerson;

    @ManyToMany(mappedBy = "participants")
    private List<Course> courses;

    // TODO NiceToHave mapping with performance for ticket selling

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getConcatPerson() {
        return concatPerson;
    }

    public void setConcatPerson(String concatPerson) {
        this.concatPerson = concatPerson;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
