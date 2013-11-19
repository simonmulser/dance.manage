package at.danceandfun.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "MANAGEMENT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Management extends Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1264262897688787556L;

}
