package at.danceandfun.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PARENT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Parent extends Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3719714853566965163L;

}
