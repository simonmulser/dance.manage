package at.danceandfun.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.ParticipantDao;
import at.danceandfun.entity.Participant;

@Service
@Transactional
public class ParticipantManagerImpl extends ManagerBaseImpl<Participant>
        implements ParticipantManager {

    public ParticipantManagerImpl() {
        super(Participant.class);
        // TODO Auto-generated constructor stub
    }

    @Autowired
    public void initializeDao(ParticipantDao participantDao) {
        setDao(participantDao);
    }

    @Override
    public List<Participant> getParticipantsByNumberOfCourses() {
        return dao
                .getQueryResults("select p"
                        + " from Participant as p"
                        + " inner join p.courses group by p.pid, p.active, p.firstname, p.lastname, p.address, p.telephone, p.password, p.email, p.birthday"
                        + " order by count(p.id) desc, p.lastname, p.firstname");

    }

    public List<Participant> getParticipantsByNumberOfSiblings() {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);
        criteria.add(Restrictions.eq("active", true));
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        return dao.getListByCriteria(criteria);

    }
}
