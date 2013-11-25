package at.danceandfun.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6848318736740069349L;

    @Id
    @Column(name = "A_ID")
    @GeneratedValue
    private Integer aid;

    @Column(name = "STREET")
    private String street;

    @Column(name = "NUMBER")
    private Integer number;

    @Column(name = "STAIR")
    private Integer stair;

    @Column(name = "DOOR")
    private Integer door;

    @Column(name = "ZIP")
    private Integer zip;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ENABLED")
    private boolean enabled;

    @OneToMany(mappedBy = "address")
    private List<Performance> performances;

    @OneToMany(mappedBy = "address")
    private List<Person> persons;

    @OneToMany(mappedBy = "address")
    private List<Course> courses;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getStair() {
        return stair;
    }

    public void setStair(Integer stair) {
        this.stair = stair;
    }

    public Integer getDoor() {
        return door;
    }

    public void setDoor(Integer door) {
        this.door = door;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    @JsonIgnore
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
