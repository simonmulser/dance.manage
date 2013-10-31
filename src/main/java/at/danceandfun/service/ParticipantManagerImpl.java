package at.danceandfun.service;

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
}
