package at.danceandfun.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import at.danceandfun.role.RoleParent;

@Entity
@Table(name = "PARENT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Parent extends Person {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "parent")
    private List<Participant> children = new ArrayList<Participant>();

    public Parent() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new RoleParent());
        return auth;
    }

    @JsonIgnore
    public List<Participant> getChildren() {
        return children;
    }

    public void setChildren(List<Participant> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
