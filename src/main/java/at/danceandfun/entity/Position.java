package at.danceandfun.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import at.danceandfun.enumeration.Duration;

@Entity
@Table(name = "POSITION")
@AssociationOverrides({
        @AssociationOverride(name = "key.invoice", joinColumns = @JoinColumn(name = "I_ID")),
        @AssociationOverride(name = "key.course", joinColumns = @JoinColumn(name = "C_ID")) })
public class Position extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PositionID key = new PositionID();

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "DURATION")
    private Integer duration;

    @Transient
    private String errorMessage;

    @Transient
    private List<Duration> possibleDurations = new ArrayList<Duration>();

    public Position() {
    }

    @EmbeddedId
    public PositionID getKey() {
        return key;
    }

    public void setKey(PositionID key) {
        this.key = key;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Duration getDuration() {
        return Duration.parse(this.duration);
    }

    public void setDuration(Duration duration) {
        this.duration = duration.getValue();
    }

    @Transient
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Transient
    public List<Duration> getPossibleDurations() {
        return possibleDurations;
    }

    public void setPossibleDurations(List<Duration> possibleDurations) {
        this.possibleDurations = possibleDurations;
    }

}
