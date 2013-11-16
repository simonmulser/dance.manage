package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Course;

@Repository
public class CourseDaoImpl extends DaoBaseImpl<Course> implements CourseDao {

    private static Logger logger = Logger.getLogger(CourseDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

}
