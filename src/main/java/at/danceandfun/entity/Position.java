package at.danceandfun.entity;

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
    private Duration duration;

    @Transient
    private String errorMessage;

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
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
