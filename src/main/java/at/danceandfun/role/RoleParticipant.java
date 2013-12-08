package at.danceandfun.role;

import org.springframework.security.core.GrantedAuthority;

public class RoleParticipant implements GrantedAuthority {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String getAuthority() {
        return "ROLE_PARTICIPANT";
    }

}
