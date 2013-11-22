package at.danceandfun.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import at.danceandfun.role.RoleUser;

@Entity
@Table(name = "PARENT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Parent extends Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3719714853566965163L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new RoleUser());
        return auth;
    }

}
