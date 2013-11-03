package at.danceandfun.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PARENT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Parent extends Person {

}
