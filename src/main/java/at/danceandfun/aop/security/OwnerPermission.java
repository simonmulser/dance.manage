package at.danceandfun.aop.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import at.danceandfun.entity.Participant;

@Component
public class OwnerPermission implements Permission {

    private static Logger logger = Logger.getLogger(OwnerPermission.class);

    public OwnerPermission() {
    }

    @Override
    public boolean isAllowed(Authentication authentication,
            Object targetDomainObject) {
        boolean hasPermission = false;

        if (isAuthenticated(authentication) && isId(targetDomainObject)) {
            Participant participant = getLogin(authentication);
            if (participant.getPid().equals(getRequestId(targetDomainObject))) {
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

    private Participant getLogin(Authentication authentication) {
        Object authenticationUserDetails = authentication.getPrincipal();
        return ((Participant) authenticationUserDetails);
    }

    private Integer getRequestId(Object targetDomainObject) {
        return (Integer) targetDomainObject;
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null
                && authentication.getPrincipal() instanceof Participant;
    }
}
