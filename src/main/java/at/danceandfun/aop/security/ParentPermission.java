package at.danceandfun.aop.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.service.ParentManager;
import at.danceandfun.service.ParticipantManager;

@Component
public class ParentPermission implements Permission {

    private static Logger logger = Logger.getLogger(ParentPermission.class);

    private ParticipantManager participantManager;

    private ParentManager parentManager;

    public ParentPermission() {
    }

    @Autowired
    public void setParentManager(ParentManager parentManager) {
        this.parentManager = parentManager;
    }

    @Autowired
    public void setParticipantManager(ParticipantManager participantManager) {
        this.participantManager = participantManager;
    }

    @Override
    public boolean isAllowed(Authentication authentication,
            Object targetDomainObject) {
        boolean hasPermission = false;

        if (isAuthenticated(authentication) && isId(targetDomainObject)) {
            Parent parent = getLogin(authentication);
            Participant child = getChild(targetDomainObject);
            if (parent.getChildren().contains(child)) {
                hasPermission = true;
            }
        }
        logger.info("return permission" + hasPermission);
        return hasPermission;
    }

    private boolean isId(Object targetDomainObject) {
        return targetDomainObject != null
                && targetDomainObject instanceof Integer;
    }

    private Parent getLogin(Authentication authentication) {
        Object authenticationUserDetails = authentication.getPrincipal();
        int id = ((Parent) authenticationUserDetails).getPid();
        return parentManager.get(id);
    }

    private Participant getChild(Object targetDomainObject) {
        int childId = (Integer) targetDomainObject;
        return participantManager.get(childId);
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null
                && authentication.getPrincipal() instanceof Parent;
    }
}
