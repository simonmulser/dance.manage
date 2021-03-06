package at.danceandfun.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.DaoBase;
import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Absence;
import at.danceandfun.entity.Participant;

@Service
@Transactional
public class ParticipantManagerImpl extends ManagerBaseImpl<Participant>
        implements ParticipantManager {

    private static Logger logger = Logger
            .getLogger(ParticipantManagerImpl.class);

    @Autowired
    private DaoBase<Absence> absenceDao;

    @Autowired
    public void setDao(DaoBaseImpl<Participant> participantDao) {
        setMainDao(participantDao);
    }

    public List<Participant> getParticipantsByNumberOfSiblings() {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);
        criteria.add(Restrictions.eq("enabled", true));
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        return mainDao.getListByCriteria(criteria);

    }

    public List<Participant> searchForSiblings(Participant actualParticipant,
            String query) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);

        Criterion rest1 = Restrictions.like("firstname", query + "%");
        Criterion rest2 = Restrictions.like("lastname", query + "%");
        criteria.add(Restrictions.or(rest1, rest2));

        criteria.add(Restrictions.eq("enabled", true));

        if (!(actualParticipant.getPid() == null)) {
            Criterion rest3 = Restrictions
                    .eq("pid", actualParticipant.getPid());
            criteria.add(Restrictions.not(rest3));
            for (Participant p : actualParticipant.getSiblings()) {
                Criterion rest4 = Restrictions.eq("pid", p.getPid());
                criteria.add(Restrictions.not(rest4));
            }
        }
        return mainDao.getListByCriteria(criteria);
    }

    @Override
    public void mergeAbsence(Absence absence) {
        logger.info("delete absence:" + absence);
        absenceDao.merge(absence);
    }

    @Override
    public List<Participant> searchForParticipants(String query) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);

        Criterion rest1 = Restrictions.like("firstname", query + "%");
        Criterion rest2 = Restrictions.like("lastname", query + "%");
        criteria.add(Restrictions.or(rest1, rest2));

        criteria.add(Restrictions.eq("enabled", true));
        return mainDao.getListByCriteria(criteria);
    }
}
