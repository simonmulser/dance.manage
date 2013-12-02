package at.danceandfun.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "INVOICE")
public class Invoice extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "I_ID")
    @GeneratedValue
    private Integer iid;

    @Column(name = "DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private DateTime date;

    @Column(name = "CANCELED")
    private boolean canceled;

    @ManyToOne
    @JoinColumn(name = "P_ID")
    private Person owner;

    @OneToMany(mappedBy = "key.invoice", cascade = CascadeType.ALL)
    private List<Position> positions = new ArrayList<Position>();

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

}
