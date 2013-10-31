package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Participant;

@Repository
public class ParticipantDaoImpl extends DaoBaseImpl<Participant> implements
        ParticipantDao {

    private static Logger logger = Logger.getLogger(ParticipantDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

}
