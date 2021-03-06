package at.danceandfun.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import at.danceandfun.util.PatternConstants;

@Entity
@Table(name = "PERSON")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person extends EntityBase implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "P_ID")
    @GeneratedValue
    private Integer pid;

    @Column(name = "FIRSTNAME")
    @Size(min = 3, message = "{size.min}")
    @Pattern(regexp = PatternConstants.CHARACTER_PATTERN, message = "{pattern.characters}")
    private String firstname;

    @Column(name = "LASTNAME")
    @Size(min = 3, message = "{size.min}")
    @Pattern(regexp = PatternConstants.CHARACTER_PATTERN, message = "{pattern.characters}")
    private String lastname;

    @Column(name = "EMAIL")
    @Pattern(regexp = PatternConstants.EMAIL_PATTERN, message = "{pattern.email}")
    private String email;

    @Column(name = "TELEPHONE")
    @Size(min = 9, message = "{size.person.telephone}")
    @Pattern(regexp = PatternConstants.TELEPHONE_PATTERN, message = "{pattern.telephone}")
    private String telephone;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "ACTIVATED", columnDefinition = "boolean default true")
    private boolean activated;

    @Column(name = "ACTIVATIONUUID")
    private String activationUUID;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "A_ID")
    @NotNull
    @Valid
    private Address address;

    public Person() {
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean isEnabled() {
        return activated;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationUUID() {
        return activationUUID;
    }

    public void setActivationUUID(String activationUUID) {
        this.activationUUID = activationUUID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pid == null) ? 0 : pid.hashCode());
        return result;
    }

    @Transient
    public String getName() {
        return firstname + " " + lastname;
    }

    @Transient
    public Integer getID() {
        return pid;
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

        Person other = (Person) obj;
        if (pid == null) {
            if (other.pid != null) {
                return false;
            }

        } else if (!pid.equals(other.pid)) {
            return false;
        }

        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

}
