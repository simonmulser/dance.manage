package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Performance;

@Repository
public class PerformanceDaoImpl extends DaoBaseImpl<Performance> implements
        PerformanceDao {

    private static Logger logger = Logger.getLogger(PerformanceDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
