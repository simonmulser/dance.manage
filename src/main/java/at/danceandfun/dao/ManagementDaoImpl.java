package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Management;

@Repository
public class ManagementDaoImpl extends DaoBaseImpl<Management> implements
        ManagementDao {

    private static Logger logger = Logger.getLogger(ManagementDaoImpl.class);

}
