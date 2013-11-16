package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Management;

@Repository
public class ManagementDaoImpl extends DaoBaseImpl<Management> implements
        ManagementDao {

    private static Logger logger = Logger.getLogger(ManagementDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

}
