package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.exception.BusinessException;

public interface ParentManager extends ManagerBase<Parent> {

    public List<Parent> searchForParents(Participant actualParticipant,
            String query);

    public void addChild(Parent parent, Participant participant, String password)
            throws BusinessException;
}
