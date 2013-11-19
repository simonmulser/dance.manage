package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Position;

@Repository
public class PositionDaoImpl extends DaoBaseImpl<Position> implements
        PositionDao {

    private static Logger logger = Logger.getLogger(PositionDaoImpl.class);

}
