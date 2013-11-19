package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Style;

@Repository
public class StyleDaoImpl extends DaoBaseImpl<Style> implements StyleDao {

    private static Logger logger = Logger.getLogger(StyleDaoImpl.class);

}
