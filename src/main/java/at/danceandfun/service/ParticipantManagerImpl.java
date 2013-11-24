package at.danceandfun.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Participant;

@Service
@Transactional
public class ParticipantManagerImpl extends ManagerBaseImpl<Participant>
        implements ParticipantManager {

    private static Logger logger = Logger
            .getLogger(ParticipantManagerImpl.class);

    @Autowired
    public void setDao(DaoBaseImpl<Participant> participantDao) {
        setMainDao(participantDao);
    }

    @Override
    public List<Participant> getParticipantsByNumberOfCourses() {
        return mainDao
                .getQueryResults("select p"
                        + " from Participant as p"
                        + " inner join p.courses group by p.pid, p.enabled, p.firstname, p.lastname, p.address, p.telephone, p.password, p.email, p.birthday"
                        + " order by count(p.id) desc, p.lastname, p.firstname");

    }

    public List<Participant> getParticipantsByNumberOfSiblings() {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);
        criteria.add(Restrictions.eq("enabled", true));
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        return mainDao.getListByCriteria(criteria);

    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);
        criteria.add(Restrictions.eq("email", username));
        criteria.add(Restrictions.eq("enabled", true));

        logger.debug("getByUsername username=" + username);

        List<Participant> participants = mainDao.getListByCriteria(criteria);
        if (participants.size() != 1) {
            logger.debug("no user found. length of retrieved list="
                    + participants.size());
            throw new UsernameNotFoundException("no user found with username="
                    + username);
        }
        logger.debug("user found. password="
                + participants.get(0).getPassword());
        return participants.get(0);
    }

    public List<String> searchForSiblings(String query) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);

        logger.debug("Search for: " + query);

        criteria.add(Restrictions.like("firstname", query + "%"));
        criteria.add(Restrictions.eq("enabled", true));
        List<Participant> siblings = mainDao.getListByCriteria(criteria);
        List<String> siblingNames = new ArrayList<String>();
        for (Participant p : siblings) {
            siblingNames.add(p.getFirstname());
        }
        return siblingNames;
    }
}
