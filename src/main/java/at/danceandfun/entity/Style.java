package at.danceandfun.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import at.danceandfun.controller.EditParticipantController;

@Entity
@Table(name = "STYLE")
public class Style implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3370722147282080437L;

    private static Logger logger = Logger
            .getLogger(EditParticipantController.class);

    @Id
    @Column(name = "S_ID")
    @GeneratedValue
    private Integer sid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ENABLED")
    private boolean enabled;

    @OneToMany(mappedBy = "style")
    private List<Course> courses = new ArrayList<Course>();

    @ManyToMany(mappedBy = "styles")
    private List<Teacher> teachers = new ArrayList<Teacher>();

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

    @JsonIgnore
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @JsonIgnore
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sid == null) ? 0 : sid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Style other = (Style) obj;
        if (sid == null) {
            if (other.sid != null) {
                return false;
            }
        } else if (!sid.equals(other.sid)) {
            return false;
        }
        return true;
    }

}
