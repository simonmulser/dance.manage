package at.danceandfun.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.DaoBase;
import at.danceandfun.entity.Admin;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Person;
import at.danceandfun.entity.SuperUser;
import at.danceandfun.entity.Teacher;

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
}
