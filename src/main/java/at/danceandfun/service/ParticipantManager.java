package at.danceandfun.service;

import java.util.List;
import java.util.Map;

import at.danceandfun.entity.Participant;

public interface ParticipantManager extends ManagerBase<Participant> {

    public Map<Integer, List<Participant>> getParticipantsByNumberOfCourses();

    public List<Participant> getActiveList();
}
