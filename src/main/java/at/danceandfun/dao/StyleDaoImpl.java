package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Style;

@Repository
public class StyleDaoImpl extends DaoBaseImpl<Style> implements StyleDao {

    private static Logger logger = Logger.getLogger(StyleDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
