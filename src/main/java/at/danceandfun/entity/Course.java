package at.danceandfun.entity;

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

@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Integer cid;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "WEEKDAY")
    // TODO Enumeration für Tag
    private Integer weekday;

    @Column(name = "TIME")
    private Time time;

    @Column(name = "ESTIMATED_SPECTATORS")
    // TODO Enumeration für Zuschauer
    private Integer estimatedSpectators;

    @Column(name = "AGEGROUP")
    // TODO Enumeartion für Altersgruppe
    private Integer ageGroup;

    @Column(name = "AMOUNT_PERFORMANCES")
    private Integer amountPerformances;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "LEVEL")
    // TODO Enumeration für Können
    private Integer level;

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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "COURSE_PERFORMANCE", joinColumns = { @JoinColumn(name = "C_ID") }, inverseJoinColumns = { @JoinColumn(name = "PER_ID") })
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

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Integer getEstimatedSpectators() {
        return estimatedSpectators;
    }

    public void setEstimatedSpectators(Integer estimatedSpectators) {
        this.estimatedSpectators = estimatedSpectators;
    }

    public Integer getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(Integer ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Integer getAmountPerformances() {
        return amountPerformances;
    }

    public void setAmountPerformances(Integer amountPerformances) {
        this.amountPerformances = amountPerformances;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
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

}
