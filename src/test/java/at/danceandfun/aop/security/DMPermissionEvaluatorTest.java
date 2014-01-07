package at.danceandfun.aop.security;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DMPermissionEvaluatorTest {

    private DMPermissionEvaluator dmPermissionEvaluator;
    private Permission permission = mock(Permission.class);
    private Map<String, Permission> permissionNameToPermissionMap;
    private Authentication authentication = mock(Authentication.class);
    private Object targetDomainObject = mock(Object.class);

    @Before
    public void setUp() {
        permissionNameToPermissionMap = new HashMap<String, Permission>();
        permissionNameToPermissionMap.put("isParent", permission);
        dmPermissionEvaluator = new DMPermissionEvaluator(
                permissionNameToPermissionMap);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHasPermissionWithTargetDomainObjectNull() {
        assertThat(dmPermissionEvaluator.hasPermission(authentication, null,
                "permission"), is(false));
    }

    @Test
    public void testHasPermissionWithAuthenticationNull() {
        assertThat(dmPermissionEvaluator.hasPermission(null,
                targetDomainObject, "permission"), is(false));
    }

    @Test
    public void testHasPermissionWithPermissionNotAString() {
        assertThat(dmPermissionEvaluator.hasPermission(authentication,
                targetDomainObject, 1), is(false));
    }

    @Test(expected = PermissionNotDefinedException.class)
    public void testHasPermissionWithPermissionNotADefined() {
        dmPermissionEvaluator.hasPermission(authentication, targetDomainObject,
                "permission not defined");
    }

    @Test
    public void testHasPermissionIsAllowedGetCalled() {
        dmPermissionEvaluator.hasPermission(authentication, targetDomainObject,
                "isParent");

        verify(permission).isAllowed(authentication, targetDomainObject);
    }
}
