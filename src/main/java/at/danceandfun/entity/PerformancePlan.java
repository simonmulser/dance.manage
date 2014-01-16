package at.danceandfun.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "PERFORMANCEPLAN")
public class PerformancePlan extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PLAN_ID")
    @GeneratedValue
    private Integer planid;

    @Column(name = "DATETIME")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @NotNull
    @Future
    private LocalDate dateTime;

    @Column(name = "ENABLED")
    private boolean enabled;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "PLAN_PERFORMANCE", joinColumns = { @JoinColumn(name = "PLAN_ID") }, inverseJoinColumns = { @JoinColumn(name = "PER_ID") })
    private List<Performance> performances = new ArrayList<Performance>();

    public PerformancePlan() {
    }

    public Integer getPlanid() {
        return planid;
    }

    public void setPlanid(Integer planid) {
        this.planid = planid;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
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
}
