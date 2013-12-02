package at.danceandfun.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author simon
 *
 */
/**
 * @author simon
 * 
 */
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
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zäöüÄÖÜ]*$", message = "darf nur aus Buchstaben bestehen")
    private String firstname;

    @Column(name = "LASTNAME")
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zäöüÄÖÜ]*$", message = "darf nur aus Buchstaben bestehen")
    private String lastname;

    @Column(name = "EMAIL")
    @NotEmpty
    @Email
    private String email;

    @Column(name = "TELEPHONE")
    @NotEmpty
    @Size(min = 9)
    private String telephone;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "BIRTHDAY")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Past
    private LocalDate birthday;

    @Column(name = "ENABLED")
    private boolean enabled;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "A_ID")
    @NotNull
    @Valid
    private Address address;

    @OneToMany(mappedBy = "owner")
    private List<Invoice> invoices = new ArrayList<Invoice>();

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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonIgnore
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonIgnore
    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pid == null) ? 0 : pid.hashCode());
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
