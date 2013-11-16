package at.danceandfun.dao;

import java.util.List;
import java.util.Map;

import at.danceandfun.entity.Participant;

public interface ParticipantDao extends DaoBase<Participant> {

    public Map<Integer, List<Participant>> getParticipantsWithNumberOfCourses();
}
