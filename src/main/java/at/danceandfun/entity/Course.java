package at.danceandfun.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostPersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.ScriptAssert;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import at.danceandfun.enumeration.AgeGroup;
import at.danceandfun.enumeration.CourseDuration;
import at.danceandfun.enumeration.CourseLevel;
import at.danceandfun.enumeration.SpectatorAmount;
import at.danceandfun.enumeration.WeekDay;
import at.danceandfun.util.Helpers;
import at.danceandfun.util.PatternConstants;

@Entity
@Table(name = "COURSE")
@ScriptAssert(lang = "javascript", script = "_this.semesterPrice <= _this.yearPrice", message = "{course.price.assertion}")
public class Course extends EntityBase implements Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Integer cid;

    @Column(name = "NAME")
    @Size(min = 3, message = "{size.min}")
    @Pattern(regexp = PatternConstants.CHARACTER_NUMBER_PATTERN, message = "{pattern.characters.numbers}")
    private String name;

    @Column(name = "SLUG")
    private String slug;

    @Column(name = "DURATION")
    @NotNull
    private Integer duration;

    @Column(name = "SEMESTERPRICE")
    @NotNull
    @Min(value = 1, message = "{min.values.positive}")
    private Double semesterPrice;

    @Column(name = "YEARPRICE")
    @NotNull
    @Min(value = 1, message = "{min.values.positive}")
    private Double yearPrice;

    @Column(name = "WEEKDAY")
    @NotNull
    private Integer weekday;

    @Column(name = "TIME")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    @NotNull
    private LocalTime time;

    @Column(name = "ESTIMATED_SPECTATORS")
    private Integer estimatedSpectators;

    @Column(name = "AGEGROUP")
    private Integer ageGroup;

    @Column(name = "AMOUNT_PERFORMANCES")
    @Min(value = 1, message = "{min.values.positive}")
    @Max(value = 3, message = "{max.value}")
    private Integer amountPerformances;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "IN_PERFORMANCE", columnDefinition = "boolean default true")
    private boolean inPerformance = true;

    @Column(name = "LEVEL")
    private Integer level;

    @Column(name = "YEAR")
    private Integer year;

    @OneToMany(mappedBy = "course")
    private List<Rating> ratings = new ArrayList<Rating>();

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "A_ID")
    @NotNull
    private Address address;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "P_ID")
    private Teacher teacher;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "S_ID")
    @NotNull
    @Valid
    private Style style;

    @OneToMany(mappedBy = "key.course", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<Position> positions = new ArrayList<Position>();

    @OneToMany(mappedBy = "course")
    private List<CourseParticipant> courseParticipants = new ArrayList<CourseParticipant>();

    @OneToMany(mappedBy = "course")
    @OrderBy(value = "number asc")
    private List<Appointment> appointments = new ArrayList<Appointment>();

    @Transient
    private boolean dummyCourse = false;

    @Transient
    private boolean balletRestriction = false;

    @Transient
    private boolean twoBreaksRestriction = false;

    @Transient
    private boolean advancedAtEndRestriction = false;

    @Transient
    private boolean balancedAmountOfSpectators = false;

    @Transient
    private boolean balancedAgeGroup = false;

    @Transient
    private boolean multipleGroupsSamePerformance = false;

    @Transient
    private boolean sibsSamePerformance = false;

    @Transient
    private Boolean violationOfRestriktions = false;

    public Course() {
    }

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public WeekDay getWeekday() {
        return WeekDay.parse(this.weekday);
    }

    public void setWeekday(WeekDay weekday) {
        this.weekday = weekday.getValue();
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public SpectatorAmount getEstimatedSpectators() {
        return SpectatorAmount.parse(this.estimatedSpectators);
    }

    public void setEstimatedSpectators(SpectatorAmount estimatedSpectators) {
        this.estimatedSpectators = estimatedSpectators.getValue();
    }

    public AgeGroup getAgeGroup() {
        return AgeGroup.parse(this.ageGroup);
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup.getValue();
    }

    public Integer getAmountPerformances() {
        return amountPerformances;
    }

    public void setAmountPerformances(Integer amountPerformances) {
        this.amountPerformances = amountPerformances;
    }

    public DateTime getStartDateTimeCurrentWeekRepresentation() {
        return Helpers.getCourseStartDateTimeCurrentWeekRepresentation(this);
    }

    public DateTime getEndDateTimeCurrentWeekRepresentation() {
        return Helpers.getCourseEndDateTimeCurrentWeekRepresentation(this);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CourseLevel getLevel() {
        return CourseLevel.parse(this.level);
    }

    public void setLevel(CourseLevel level) {
        this.level = level.getValue();
    }

    @JsonIgnore
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

    // @JsonIgnore
    // public List<Performance> getPerformances() {
    // return performances;
    // }
    //
    // public void setPerformances(List<Performance> performances) {
    // this.performances = performances;
    // }

    @JsonIgnore
    public List<CourseParticipant> getCourseParticipants() {
        return courseParticipants;
    }

    public void setCourseParticipants(List<CourseParticipant> courseParticipants) {
        this.courseParticipants = courseParticipants;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public void setEstimatedSpectators(Integer estimatedSpectators) {
        this.estimatedSpectators = estimatedSpectators;
    }

    public void setAgeGroup(Integer ageGroup) {
        this.ageGroup = ageGroup;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isInPerformance() {
        return inPerformance;
    }

    public void setInPerformance(boolean inPerformance) {
        this.inPerformance = inPerformance;
    }

    @JsonIgnore
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public boolean isDummyCourse() {
        return dummyCourse;
    }

    public void setDummyCourse(boolean dummyCourse) {
        this.dummyCourse = dummyCourse;
    }

    public Boolean getBalletRestriction() {
        return balletRestriction;
    }

    public void setBalletRestriction(Boolean balletRestriction) {
        this.balletRestriction = balletRestriction;
    }

    public Boolean getTwoBreaksRestriction() {
        return twoBreaksRestriction;
    }

    public void setTwoBreaksRestriction(Boolean twoBreaksRestriction) {
        this.twoBreaksRestriction = twoBreaksRestriction;
    }

    public Boolean getAdvancedAtEndRestriction() {
        return advancedAtEndRestriction;
    }

    public void setAdvancedAtEndRestriction(Boolean advancedAtEndRestriction) {
        this.advancedAtEndRestriction = advancedAtEndRestriction;
    }

    public Boolean getBalancedAmountOfSpectators() {
        return balancedAmountOfSpectators;
    }

    public void setBalancedAmountOfSpectators(Boolean balancedAmountOfSpectators) {
        this.balancedAmountOfSpectators = balancedAmountOfSpectators;
    }

    public boolean isBalancedAgeGroup() {
        return balancedAgeGroup;
    }

    public void setBalancedAgeGroup(boolean balancedAgeGroup) {
        this.balancedAgeGroup = balancedAgeGroup;
    }

    public boolean isMultipleGroupsSamePerformance() {
        return multipleGroupsSamePerformance;
    }

    public void setMultipleGroupsSamePerformance(
            boolean multipleGroupsSamePerformance) {
        this.multipleGroupsSamePerformance = multipleGroupsSamePerformance;
    }

    public boolean isSibsSamePerformance() {
        return sibsSamePerformance;
    }

    public void setSibsSamePerformance(boolean sibsSamePerformance) {
        this.sibsSamePerformance = sibsSamePerformance;
    }

    public Boolean getViolationOfRestriktions() {
        return violationOfRestriktions;
    }

    public void setViolationOfRestriktions(Boolean violationOfRestriktions) {
        this.violationOfRestriktions = violationOfRestriktions;
    }

    @PreUpdate
    @Override
    public void onUpdate() {
        slug = Helpers.toSlug(name + " " + cid);

    }

    @PostPersist
    public void postCreate() {
        slug = Helpers.toSlug(name + " " + cid);
    }

    @Transient
    public String getIcon() {
        return "icon-calendar";
    }

    @Transient
    public Integer getID() {
        return cid;
    }

    @Transient
    public String getObjectName() {
        return "course";
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
