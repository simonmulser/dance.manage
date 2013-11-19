package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Parent;

@Repository
public class ParentDaoImpl extends DaoBaseImpl<Parent> implements ParentDao {

    private static Logger logger = Logger.getLogger(ParentDaoImpl.class);

}
