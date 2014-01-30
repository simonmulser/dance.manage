package at.danceandfun.aop.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

public class DMPermissionEvaluator implements PermissionEvaluator {

    private static Logger logger = Logger
            .getLogger(DMPermissionEvaluator.class);

    private Map<String, Permission> permissionNameToPermissionMap = new HashMap<String, Permission>();

    protected DMPermissionEvaluator() {
    }

    public DMPermissionEvaluator(
            Map<String, Permission> permissionNameToPermissionMap) {
        this.permissionNameToPermissionMap = permissionNameToPermissionMap;
    }

    @Override
    @Transactional
    public boolean hasPermission(Authentication authentication,
            Object targetDomainObject, Object permission) {
        logger.info("entered hasPermission");

        boolean hasPermission = false;
        if (canHandle(authentication, targetDomainObject, permission)) {
            hasPermission = checkPermission(authentication, targetDomainObject,
                    (String) permission);
        }
        return hasPermission;
    }

    private boolean canHandle(Authentication authentication,
            Object targetDomainObject, Object permission) {
        return targetDomainObject != null && authentication != null
                && permission instanceof String;
    }

    private boolean checkPermission(Authentication authentication,
            Object targetDomainObject, String permissionKey) {
        verifyPermissionIsDefined(permissionKey);
        Permission permission = permissionNameToPermissionMap
                .get(permissionKey);
        return permission.isAllowed(authentication, targetDomainObject);
    }

    private void verifyPermissionIsDefined(String permissionKey) {
        if (!permissionNameToPermissionMap.containsKey(permissionKey)) {
            throw new PermissionNotDefinedException("No permission with key "
                    + permissionKey + " is defined in "
                    + this.getClass().toString());
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication,
            Serializable targetId, String targetType, Object permission) {
        throw new PermissionNotDefinedException(
                "Id and Class permissions are not supperted by "
                        + this.getClass().toString());
    }
}
