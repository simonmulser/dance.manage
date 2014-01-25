package at.danceandfun.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.ParticipantDaoTest;
import at.danceandfun.entity.Person;
import at.danceandfun.util.Helpers;
import at.danceandfun.util.PasswordBean;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonManagerTest {

    @Autowired
    private PersonManager personManager;

    @Test
    public void testFailsOldPasswordFalse() {
        Person person = ParticipantDaoTest.getValidParticipant();
        person.setPassword(Helpers.PASSWORD_FOR_DUMMY_ACCOUNTS);

        personManager.persist(person);

        assertThat(personManager.changePassword(getPasswordBean(person.getID(),
                "abb", "password", "password")), is(false));
    }

    @Test
    public void testFailsNewPasswordsNotEqual() {
        Person person = ParticipantDaoTest.getValidParticipant();
        person.setPassword(Helpers.PASSWORD_FOR_DUMMY_ACCOUNTS);

        personManager.persist(person);

        assertThat(personManager.changePassword(getPasswordBean(person.getID(),
                "abc", "password", "password1")), is(false));
    }

    @Test
    public void testFailsNewPasswordEqualsOldPassword() {
        Person person = ParticipantDaoTest.getValidParticipant();
        person.setPassword(Helpers.PASSWORD_FOR_DUMMY_ACCOUNTS);

        personManager.persist(person);

        assertThat(personManager.changePassword(getPasswordBean(person.getID(),
                "abc", "abc", "abc")), is(false));
    }

    @Test
    public void testOKPasswordChangeSuccessfull() {
        Person person = ParticipantDaoTest.getValidParticipant();
        person.setPassword(Helpers.PASSWORD_FOR_DUMMY_ACCOUNTS);

        personManager.persist(person);

        assertThat(personManager.changePassword(getPasswordBean(person.getID(),
                "abc", "password", "password")), is(true));
    }

    public PasswordBean getPasswordBean(Integer id, String oldPassword,
            String newPasswordFirst,
            String newPasswordSecond) {
        PasswordBean passwordBean = new PasswordBean();

        passwordBean.setId(id);
        passwordBean.setOldPassword(oldPassword);
        passwordBean.setNewPasswordFirst(newPasswordFirst);
        passwordBean.setNewPasswordSecond(newPasswordSecond);

        return passwordBean;
    }

}
