package at.danceandfun.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import at.danceandfun.entity.Participant;

public interface ParticipantManager extends UserDetailsService,
        ManagerBase<Participant> {

    public List<Participant> getParticipantsByNumberOfCourses();

    public List<Participant> getParticipantsByNumberOfSiblings();

    public List searchForSiblings(Participant actualParticipant, String query);
}
