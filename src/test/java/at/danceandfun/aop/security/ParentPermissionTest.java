package at.danceandfun.aop.security;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.ParentDaoTest;
import at.danceandfun.dao.ParticipantDaoTest;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.service.ParentManager;
import at.danceandfun.service.ParticipantManager;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParentPermissionTest {

    private ParentPermission parentPermission;
    private Authentication authentication = mock(Authentication.class);
    private Object targetDomainObject = mock(Object.class);
    private Parent parent;
    private Participant child;
    private ParentManager parentManager = mock(ParentManager.class);
    private ParticipantManager participantManager = mock(ParticipantManager.class);

    @Before
    public void setUp() {
        parentPermission = new ParentPermission();
        parentPermission.setParentManager(parentManager);
        parentPermission.setParticipantManager(participantManager);

        parent = ParentDaoTest.getValidParent();
        parent.setPid(1);
        child = ParticipantDaoTest.getValidParticipant();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testIsAllowedWithAuthenticationNull() {
        assertThat(parentPermission.isAllowed(null, targetDomainObject),
                is(false));
    }

    @Test
    public void testIsAllowedWithGetPrincipalNotParent() {
        when(authentication.getPrincipal()).thenReturn("not a parent object");

        assertThat(
                parentPermission.isAllowed(authentication, targetDomainObject),
                is(false));
    }

    @Test
    public void testIsAllowedWithTargetDomainObjectNull() {
        when(authentication.getPrincipal()).thenReturn(parent);

        assertThat(parentPermission.isAllowed(authentication, null), is(false));
    }

    @Test
    public void testIsAllowedWithTargetDomainObjectNotInteger() {
        when(authentication.getPrincipal()).thenReturn(parent);

        assertThat(parentPermission.isAllowed(authentication,
                "not a integer object"), is(false));
    }

    @Test
    public void testIsAllowedithParticipantIsNotAChildOfParent() {
        when(authentication.getPrincipal()).thenReturn(parent);
        when(parentManager.get(anyInt())).thenReturn(parent);
        when(participantManager.get(anyInt())).thenReturn(child);

        assertThat(parentPermission.isAllowed(authentication, 1), is(false));
    }

    @Test
    public void testIsAllowedWhenParticipantAsAChildOfParent() {
        when(authentication.getPrincipal()).thenReturn(parent);
        when(parentManager.get(anyInt())).thenReturn(parent);
        when(participantManager.get(anyInt())).thenReturn(child);

        parent.getChildren().add(child);

        assertThat(parentPermission.isAllowed(authentication, 1), is(true));
    }
}
