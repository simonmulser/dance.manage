package at.danceandfun.role;

import org.springframework.security.core.GrantedAuthority;

public class RoleParent implements GrantedAuthority {

    /**
     * 
     */
    private static final long serialVersionUID = -4184308166366598483L;

    @Override
    public String getAuthority() {
        return "ROLE_PARENT";
    }

}
