package at.danceandfun.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import at.danceandfun.entity.Person;

public interface PersonManager extends UserDetailsService {
    
    public Person getURLToken(Person person);
    
    public Person sendURL(Person person);

    public Person getPersonByActivationUUID(String uuid);
    
    public Person update(Person person);

}
