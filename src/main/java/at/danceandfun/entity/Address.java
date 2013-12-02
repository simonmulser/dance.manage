package at.danceandfun.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
@Table(name = "ADDRESS")
public class Address extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "A_ID")
    @GeneratedValue
    private Integer aid;

    @Column(name = "STREET")
    @NotEmpty
    @NumberFormat(style = Style.NUMBER)
    @Pattern(regexp = "^[A-Za-zäöüÄÖÜ]*$", message = "darf nur aus Buchstaben bestehen")
    private String street;

    @Column(name = "NUMBER")
    @NotNull
    @Min(1)
    private Integer number;

    @Column(name = "STAIR")
    private Integer stair;

    @Column(name = "DOOR")
    private Integer door;

    @Column(name = "ZIP")
    @NotNull
    @Min(1)
    private Integer zip;

    @Column(name = "CITY")
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zäöüÄÖÜ]*$", message = "darf nur aus Buchstaben bestehen")
    private String city;

    @Column(name = "ENABLED")
    private boolean enabled;

    @OneToMany(mappedBy = "address")
    private List<Performance> performances = new ArrayList<Performance>();

    @OneToMany(mappedBy = "address")
    private List<Person> persons = new ArrayList<Person>();

    @OneToMany(mappedBy = "address")
    private List<Course> courses = new ArrayList<Course>();

    public Address() {
    }

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

    @JsonIgnore
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

    @JsonIgnore
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aid == null) ? 0 : aid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Address other = (Address) obj;
        if (aid == null) {
            if (other.aid != null) {
                return false;
            }
        } else if (!aid.equals(other.aid)) {
            return false;
        }
        return true;
    }

}
