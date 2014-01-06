package at.danceandfun.service;

import static org.mockito.Mockito.mock;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.dao.ParentDaoTest;
import at.danceandfun.dao.ParticipantDaoTest;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.exception.BusinessException;
import at.danceandfun.util.Helpers;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParentManagerTest {

    private static Logger logger = Logger.getLogger(ParentManagerTest.class);

    @Autowired
    private ParentManagerImpl parentManager;

    @SuppressWarnings("unchecked")
    private DaoBaseImpl<Parent> mockedDao = (DaoBaseImpl<Parent>) mock(DaoBaseImpl.class);

    private PersonManager personManager = (PersonManager) mock(PersonManager.class);

    private DaoBaseImpl<Parent> dao;

    @Before
    public void setUp() {
        dao = parentManager.getMainDao();
        parentManager.setDao(mockedDao);
        parentManager.setPersonManager(personManager);

    }

    @After
    public void tearDown() {
        parentManager.setDao(dao);
    }

    @Test
    public void testAddChild() {
        Parent parent = ParentDaoTest.getValidParent();
        Participant participant = ParticipantDaoTest.getValidParticipant();

        parentManager.addChild(parent, participant,
                Helpers.PASSWORD_FOR_DUMMY_ACCOUNTS);
    }

    @Test(expected = BusinessException.class)
    public void testAddChildWithWrongPassword() {
        Parent parent = ParentDaoTest.getValidParent();
        Participant participant = ParticipantDaoTest.getValidParticipant();

        parentManager.addChild(parent, participant, "wrong password");

    }

    @Test(expected = BusinessException.class)
    public void testAddChildWithAlreadyChild() {
        Parent parent = ParentDaoTest.getValidParent();
        Participant participant = ParticipantDaoTest.getValidParticipant();
        parent.getChildren().add(participant);

        parentManager.addChild(parent, participant,
                Helpers.PASSWORD_FOR_DUMMY_ACCOUNTS);
    }
}
