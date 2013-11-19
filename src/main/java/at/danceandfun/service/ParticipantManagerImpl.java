package at.danceandfun.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
    public Map<Integer, List<Participant>> getParticipantsByNumberOfCourses() {
        Iterator<Object> iterator = dao
                .getQueryResults("select p, count(p.id)"
                        + " from Participant as p"
                        + " inner join p.courses group by p.pid, p.active, p.firstname, p.lastname, p.address, p.telephone, p.password, p.email, p.birthday"
                        + " order by count(p.id) desc, p.lastname, p.firstname");

        SortedMap<Integer, List<Participant>> map = new TreeMap<Integer, List<Participant>>(
                Collections.reverseOrder());
        while (iterator.hasNext()) {
            Object[] tuple = (Object[]) iterator.next();
            Integer numberOfCourses = ((Long) tuple[1]).intValue();
            List<Participant> participants = map.get(numberOfCourses);
            if (participants == null) {
                participants = new ArrayList<Participant>();
                participants.add((Participant) tuple[0]);
                map.put(numberOfCourses, participants);
            } else {
                participants.add((Participant) tuple[0]);
            }
        }
        return map;
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
