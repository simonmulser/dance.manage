package at.danceandfun.role;

import org.springframework.security.core.GrantedAuthority;

public class RoleParticipant implements GrantedAuthority {

    /**
     * 
     */
    private static final long serialVersionUID = 5848435287479058926L;

    @Override
    public String getAuthority() {
        return "ROLE_PARTICIPANT";
    }

}
