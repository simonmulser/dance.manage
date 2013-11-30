package at.danceandfun.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import at.danceandfun.enumeration.AgeGroup;
import at.danceandfun.enumeration.CourseLevel;
import at.danceandfun.enumeration.SpectatorAmount;
import at.danceandfun.enumeration.WeekDay;

@Entity
@Table(name = "COURSE")
public class Course implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6088131134219471148L;

    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Integer cid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "WEEKDAY")
    private WeekDay weekday;

    @Column(name = "TIME")
    private Time time;

    @Column(name = "ESTIMATED_SPECTATORS")
    private SpectatorAmount estimatedSpectators;

    @Column(name = "AGEGROUP")
    private AgeGroup ageGroup;

    @Column(name = "AMOUNT_PERFORMANCES")
    private Integer amountPerformances;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "LEVEL")
    private CourseLevel level;

    @OneToMany(mappedBy = "course")
    private List<Rating> ratings = new ArrayList<Rating>();

    @ManyToOne
    @JoinColumn(name = "A_ID")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "P_ID")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "S_ID")
    private Style style;

    @ManyToMany(mappedBy = "courses")
    private List<Performance> performances = new ArrayList<Performance>();

    @ManyToMany(mappedBy = "courses")
    private List<Participant> participants = new ArrayList<Participant>();

    @OneToMany(mappedBy = "key.course", cascade = CascadeType.ALL)
    private List<Position> positions = new ArrayList<Position>();

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public WeekDay getWeekday() {
        return weekday;
    }

    public void setWeekday(WeekDay weekday) {
        this.weekday = weekday;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public SpectatorAmount getEstimatedSpectators() {
        return estimatedSpectators;
    }

    public void setEstimatedSpectators(SpectatorAmount estimatedSpectators) {
        this.estimatedSpectators = estimatedSpectators;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Integer getAmountPerformances() {
        return amountPerformances;
    }

    public void setAmountPerformances(Integer amountPerformances) {
        this.amountPerformances = amountPerformances;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CourseLevel getLevel() {
        return level;
    }

    public void setLevel(CourseLevel level) {
        this.level = level;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    @JsonIgnore
    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    @JsonIgnore
    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    @JsonIgnore
    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "ID: " + cid + "NAME: " + name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cid == null) ? 0 : cid.hashCode());
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
        Course other = (Course) obj;
        if (cid == null) {
            if (other.cid != null) {
                return false;
            }
        } else if (!cid.equals(other.cid)) {
            return false;
        }
        return true;
    }

}
