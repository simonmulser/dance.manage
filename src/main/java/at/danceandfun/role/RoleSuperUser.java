package at.danceandfun.role;

import org.springframework.security.core.GrantedAuthority;

public class RoleSuperUser implements GrantedAuthority {

    /**
     * 
     */
    private static final long serialVersionUID = 1472341919260465400L;

    @Override
    public String getAuthority() {
        return "ROLE_SUPERUSER";
    }

}
