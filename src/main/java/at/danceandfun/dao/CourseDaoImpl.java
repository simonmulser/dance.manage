package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Course;

@Repository
public class CourseDaoImpl extends DaoBaseImpl<Course> implements CourseDao {

    private static Logger logger = Logger.getLogger(CourseDaoImpl.class);

}
