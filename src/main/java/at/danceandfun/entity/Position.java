package at.danceandfun.entity;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "POSITION")
@AssociationOverrides({
        @AssociationOverride(name = "key.invoice", joinColumns = @JoinColumn(name = "INVOICE_ID")),
        @AssociationOverride(name = "key.course", joinColumns = @JoinColumn(name = "COURSE_ID")) })
public class Position implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5049666362328255024L;

    private PositionID key = new PositionID();

    @Column(name = "AMOUNT")
    private String amount;

    @EmbeddedId
    public PositionID getKey() {
        return key;
    }

    public void setKey(PositionID key) {
        this.key = key;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
