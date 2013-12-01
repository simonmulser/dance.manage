package at.danceandfun.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "STYLE")
public class Style extends EntityBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "S_ID")
    @GeneratedValue
    private Integer sid;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "style")
    private List<Course> courses;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "STYLE_TEACHER", joinColumns = { @JoinColumn(name = "S_ID") }, inverseJoinColumns = { @JoinColumn(name = "P_ID") })
    private List<Teacher> teachers;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

}
