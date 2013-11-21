package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Participant;

public interface ParticipantManager extends ManagerBase<Participant> {

    public List<Participant> getParticipantsByNumberOfCourses();

    public List<Participant> getParticipantsByNumberOfSiblings();

    public List<Participant> getActiveList();
}
