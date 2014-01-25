package at.danceandfun.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import at.danceandfun.entity.Person;
import at.danceandfun.util.PasswordBean;

public interface PersonManager extends UserDetailsService {
    
    public Person getURLToken(Person person);
    
    public Person sendURL(Person person);

    public Person getPersonByActivationUUID(String uuid);
    
    public Person get(int id);

    public void persist(Person person);

    public Person update(Person person);
    
    public boolean changePassword(PasswordBean passwordBean);

}
