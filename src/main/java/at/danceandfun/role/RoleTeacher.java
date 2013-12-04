package at.danceandfun.role;

import org.springframework.security.core.GrantedAuthority;

public class RoleTeacher implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return "ROLE_TEACHER";
    }

}
