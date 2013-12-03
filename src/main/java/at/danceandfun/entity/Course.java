package at.danceandfun.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import at.danceandfun.enumeration.AgeGroup;
import at.danceandfun.enumeration.CourseDuration;
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
    private int duration;

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
    private List<Rating> ratings;

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
    private List<Performance> performances;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "COURSE_PARTICIPANT", joinColumns = { @JoinColumn(name = "C_ID") }, inverseJoinColumns = { @JoinColumn(name = "P_ID") })
    private List<Participant> participants;

    @OneToMany(mappedBy = "key.course", cascade = CascadeType.ALL)
    private List<Position> positions;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public CourseDuration getDuration() {
        return CourseDuration.parse(this.duration);
    }

    public void setDuration(CourseDuration duration) {
        this.duration = duration.getValue();
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

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

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
}
