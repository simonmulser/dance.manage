package at.danceandfun.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "PERFORMANCE")
public class Performance {

    @Id
    @Column(name = "PER_ID")
    @GeneratedValue
    private Integer perid;

    @Column(name = "DATETIME")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private DateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "A_ID")
    private Address address;

    @ManyToMany(mappedBy = "performances")
    private List<Course> courses;

    // TODO NiceToHave mapping with person/participant for ticket selling

    public Integer getPerid() {
        return perid;
    }

    public void setPerid(Integer perid) {
        this.perid = perid;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
