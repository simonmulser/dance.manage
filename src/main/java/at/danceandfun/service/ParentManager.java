package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;

public interface ParentManager extends ManagerBase<Parent> {
    public List<Parent> searchForParents(Participant actualParticipant,
            String query);
}
