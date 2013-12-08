package at.danceandfun.entity;

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
        @AssociationOverride(name = "key.invoice", joinColumns = @JoinColumn(name = "I_ID")),
        @AssociationOverride(name = "key.course", joinColumns = @JoinColumn(name = "C_ID")) })
public class Position extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PositionID key = new PositionID();

    @Column(name = "AMOUNT")
    private String amount;

    public Position() {
    }

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
