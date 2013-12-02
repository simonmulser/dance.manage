package at.danceandfun.entity;

import java.sql.Time;
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

import at.danceandfun.enumeration.AgeGroup;
import at.danceandfun.enumeration.CourseLevel;
import at.danceandfun.enumeration.SpectatorAmount;
import at.danceandfun.enumeration.WeekDay;

@Entity
@Table(name = "COURSE")
public class Course extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Integer cid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "SEMESTERPRICE")
    private Double semesterPrice;

    @Column(name = "YEARPRICE")
    private Double yearPrice;

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

    @Column(name = "YEAR")
    private int year;

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

    // TODO remove
    // @ManyToMany(cascade = { CascadeType.ALL })
    // @JoinTable(name = "COURSE_PARTICIPANT", joinColumns = { @JoinColumn(name
    // = "C_ID") }, inverseJoinColumns = { @JoinColumn(name = "P_ID") })
    // private List<Participant> participants;

    @OneToMany(mappedBy = "key.course", cascade = CascadeType.ALL)
    private List<Position> positions;

    @OneToMany(mappedBy = "key.course", cascade = CascadeType.ALL)
    private List<CourseParticipant> courseParticipants;

    public Course() {
    }

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

    public Double getSemesterPrice() {
        return semesterPrice;
    }

    public void setSemesterPrice(Double semesterPrice) {
        this.semesterPrice = semesterPrice;
    }

    public Double getYearPrice() {
        return yearPrice;
    }

    public void setYearPrice(Double yearPrice) {
        this.yearPrice = yearPrice;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public List<CourseParticipant> getCourseParticipants() {
        return courseParticipants;
    }

    public void setCourseParticipants(List<CourseParticipant> courseParticipants) {
        this.courseParticipants = courseParticipants;
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
