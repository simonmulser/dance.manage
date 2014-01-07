package at.danceandfun.aop.security;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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

import at.danceandfun.dao.ParticipantDaoTest;
import at.danceandfun.entity.Participant;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OwnerPermissionTest {

    private OwnerPermission ownerPermission;
    private Authentication authentication = mock(Authentication.class);
    private Object targetDomainObject = mock(Object.class);
    private Participant participant;

    @Before
    public void setUp() {
        ownerPermission = new OwnerPermission();

        participant = ParticipantDaoTest.getValidParticipant();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testIsAllowedWithAuthenticationNull() {
        assertThat(ownerPermission.isAllowed(null, targetDomainObject),
                is(false));
    }

    @Test
    public void testIsAllowedWithGetPrincipalNotParticipantt() {
        when(authentication.getPrincipal()).thenReturn("not a parent object");

        assertThat(
                ownerPermission.isAllowed(authentication, targetDomainObject),
                is(false));
    }

    @Test
    public void testIsAllowedWithTargetDomainObjectNull() {
        when(authentication.getPrincipal()).thenReturn(participant);

        assertThat(ownerPermission.isAllowed(authentication, null), is(false));
    }

    @Test
    public void testIsAllowedWithTargetDomainObjectNotInteger() {
        when(authentication.getPrincipal()).thenReturn(participant);

        assertThat(ownerPermission.isAllowed(authentication,
                "not a integer object"), is(false));
    }

    @Test
    public void testIsAllowedithParticipantIdIsNotEqual() {
        when(authentication.getPrincipal()).thenReturn(participant);
        participant.setPid(99);

        assertThat(ownerPermission.isAllowed(authentication, 1), is(false));
    }

    @Test
    public void testIsAllowedWhenParticipantIdIsEqual() {
        when(authentication.getPrincipal()).thenReturn(participant);
        participant.setPid(1);

        assertThat(ownerPermission.isAllowed(authentication, 1), is(true));
    }
}
