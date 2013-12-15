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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "APPOINTMENT")
@PrimaryKeyJoinColumn(name = "AP_ID")
public class Appointment extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AP_ID")
    @GeneratedValue
    private Integer apid;

    @Column(name = "APPOINTMENT_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate appointmentDate;

    @Column(name = "NUMBER")
    private int number;

    @Column(name = "ENABLED")
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "C_ID")
    private Course course;

    @OneToMany(mappedBy = "key.appointment", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    private List<Absence> absences = new ArrayList<Absence>();

    public Appointment() {
    }

    public Integer getApid() {
        return apid;
    }

    public void setApid(Integer apid) {
        this.apid = apid;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    @Override
    public String toString() {
        return "course(" + course + ") number:" + number;
    }
}
