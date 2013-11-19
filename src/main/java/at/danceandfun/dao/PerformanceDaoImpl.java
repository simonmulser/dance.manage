package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Performance;

@Repository
public class PerformanceDaoImpl extends DaoBaseImpl<Performance> implements
        PerformanceDao {

    private static Logger logger = Logger.getLogger(PerformanceDaoImpl.class);

}
