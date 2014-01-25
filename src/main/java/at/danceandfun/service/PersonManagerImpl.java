package at.danceandfun.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.DaoBase;
import at.danceandfun.entity.Admin;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Person;
import at.danceandfun.entity.SuperUser;
import at.danceandfun.entity.Teacher;
import at.danceandfun.util.AppContext;
import at.danceandfun.util.EMailAuthentication;
import at.danceandfun.util.PasswordBean;

@Service
@Transactional
public class PersonManagerImpl implements PersonManager {

    private Logger logger = Logger.getLogger(PersonManagerImpl.class);

    @Autowired
    private DaoBase<Participant> participantDao;

    @Autowired
    private DaoBase<Teacher> teacherDao;

    @Autowired
    private DaoBase<Admin> adminDao;

    @Autowired
    private DaoBase<SuperUser> superUserDao;

    @Autowired
    private DaoBase<Parent> parentDao;

    @Autowired
    private DaoBase<Person> personDao;

    @Autowired
    private EMailAuthentication emailAuthentication;

    private StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();

    @SuppressWarnings("unchecked")
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        List<DaoBase<? extends Person>> personDaos = new ArrayList<>();
        personDaos.add(participantDao);
        personDaos.add(adminDao);
        personDaos.add(parentDao);
        personDaos.add(teacherDao);
        personDaos.add(superUserDao);

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("email", username));
        criterions.add(Restrictions.eq("enabled", true));

        for (DaoBase<? extends Person> dao : personDaos) {
            List<Person> persons = (List<Person>) dao
                    .getListByCriterions(criterions);
            if (persons.size() > 0) {
                return persons.get(0);
            }
        }

        logger.info("no user found with username " + username);
        throw new UsernameNotFoundException("no user found with username "
                + username);
    }

    public Person getURLToken(Person person) {
        logger.info("Setting activation token to person with ID: "
                + person.getPid());
        person.setActivated(false);
        person.setActivationUUID(UUID.randomUUID().toString());
        return person;
    }

    public Person sendURL(Person person) {
        logger.info("Sending activation URL to person with ID: "
                + person.getPid());
        emailAuthentication.sendMail(person);
        return person;
    }

    @SuppressWarnings("unchecked")
    public Person getPersonByActivationUUID(String uuid) {
        logger.info("Checks the UUID arrived from the link");
        List<DaoBase<? extends Person>> personDaos = new ArrayList<>();
        personDaos.add(participantDao);
        personDaos.add(parentDao);
        personDaos.add(teacherDao);

        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.eq("activationUUID", uuid));
        criterions.add(Restrictions.eq("enabled", true));

        for (DaoBase<? extends Person> dao : personDaos) {
            List<Person> persons = (List<Person>) dao
                    .getListByCriterions(criterions);
            if (persons.size() > 0) {
                return persons.get(0);
            }
        }
        return null;
    }

    public Person update(Person person) {
        logger.info("Update person object with ID: " + person.getPid());
        return personDao.merge(person);
    }

    public Person get(int id) {
        logger.info("Get person object with ID: " + id);
        return personDao.get(id);
    }

    public void persist(Person person) {
        logger.info("Presist person object");
        personDao.persist(person);
    }

    public boolean changePassword(PasswordBean passwordBean) {
        logger.info("Change password with ID: " + passwordBean.getId());
        logger.info("Password Changes: " + passwordBean.getOldPassword()
                + passwordBean.getNewPasswordFirst()
                + passwordBean.getNewPasswordSecond());
        Person person = personDao.get(passwordBean.getId());
        
        if (!passwordEncoder.matches(passwordBean.getOldPassword(),
                person.getPassword())) {
            logger.error("Old password not right from user: "
                    + passwordBean.getId());
            passwordBean
                    .setErrorMessage(geti18nMessage("message.password.oldFalse"));

            return false;
        } else if (!passwordBean.getNewPasswordFirst().equals(
                passwordBean.getNewPasswordSecond())) {
            logger.error("Passwords not equal from user: "
                    + passwordBean.getId());
            passwordBean
                    .setErrorMessage(geti18nMessage("message.password.equals"));

            return false;

        } else if (passwordEncoder.matches(passwordBean.getNewPasswordFirst(),
                person.getPassword())) {
            logger.error("New password not different from user: "
                    + passwordBean.getId());

            passwordBean
                    .setErrorMessage(geti18nMessage("message.password.newPassword"));

            return false;

        } else {
            logger.info("Change password with ID: " + passwordBean.getId());
            passwordBean.setErrorMessage("");
            person.setPassword(passwordEncoder.encode(passwordBean
                    .getNewPasswordFirst()));
            personDao.merge(person);

            return true;
        }
    }

    public String geti18nMessage(String identifier) {
        Locale locale = LocaleContextHolder.getLocale();
        return AppContext.getApplicationContext().getMessage(identifier, null,
                locale);
    }
}
