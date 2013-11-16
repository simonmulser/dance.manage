package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Parent;

@Repository
public class ParentDaoImpl extends DaoBaseImpl<Parent> implements ParentDao {

    private static Logger logger = Logger.getLogger(ParentDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

}
