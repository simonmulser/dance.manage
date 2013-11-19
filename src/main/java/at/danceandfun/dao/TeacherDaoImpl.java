package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Teacher;

@Repository
public class TeacherDaoImpl extends DaoBaseImpl<Teacher> implements TeacherDao {

    private static Logger logger = Logger.getLogger(TeacherDaoImpl.class);

}
