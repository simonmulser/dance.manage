package at.danceandfun.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class PositionID implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Invoice invoice;
    private Course course;

    @ManyToOne
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @ManyToOne
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
