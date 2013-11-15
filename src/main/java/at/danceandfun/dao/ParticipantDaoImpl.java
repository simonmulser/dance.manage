package at.danceandfun.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Participant;

@Repository
public class ParticipantDaoImpl extends DaoBaseImpl<Participant> implements
        ParticipantDao {

    private static Logger logger = Logger.getLogger(ParticipantDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Participant> getList() {
        logger.debug("loadAll");
        Criteria criteria = getCurrentSession().createCriteria(
                Participant.class);
        criteria.add(Restrictions.eq("active", true));
        return criteria.list();
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
