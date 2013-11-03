package at.danceandfun.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "MANAGEMENT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Management extends Person {

}
