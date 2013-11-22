package at.danceandfun.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import at.danceandfun.role.RoleAdmin;

@Entity
@Table(name = "MANAGEMENT")
@PrimaryKeyJoinColumn(name = "P_ID")
public class Management extends Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1264262897688787556L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new RoleAdmin());
        return auth;
    }
}
