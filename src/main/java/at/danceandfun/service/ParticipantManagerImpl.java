package at.danceandfun.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.ParticipantDao;
import at.danceandfun.entity.Participant;

@Service
public class ParticipantManagerImpl extends ManagerBaseImpl<Participant>
        implements ParticipantManager {

    private ParticipantDao participantDao;

    @Autowired
    public void initializeDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
        setDao(participantDao);
    }

    @Override
    public Map<Integer, List<Participant>> getParticipantsByNumberOfCourses() {

        return participantDao.getParticipantsWithNumberOfCourses();
    }

    public List<Participant> getActiveList() {
        DetachedCriteria active = DetachedCriteria.forClass(Participant.class);
        active.add(Restrictions.eq("active", true));
        return participantDao.getListByCriteria(active);
    }

}
