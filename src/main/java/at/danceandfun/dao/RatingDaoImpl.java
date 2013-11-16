package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Rating;

@Repository
public class RatingDaoImpl extends DaoBaseImpl<Rating> implements RatingDao {

    private static Logger logger = Logger.getLogger(RatingDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

}
