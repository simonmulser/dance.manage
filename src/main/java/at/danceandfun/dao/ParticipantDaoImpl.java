package at.danceandfun.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
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
    @Override
    public Map<Integer, List<Participant>> getParticipantsWithNumberOfCourses() {
        Iterator<Object> list = getCurrentSession()
                .createQuery(
                        "select p, count(p.id)"
                                + " from Participant as p"
                                + " inner join p.courses group by p.pid, p.active, p.firstname, p.lastname, p.address, p.telephone, p.password, p.email, p.birthday")
                .list().iterator();
        Map<Integer, List<Participant>> map = new HashMap<Integer, List<Participant>>();
        while (list.hasNext()) {
            Object[] tuple = (Object[]) list.next();
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
}
