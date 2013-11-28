package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Admin;

@Service
public class AdminManagerImpl extends ManagerBaseImpl<Admin> implements
        AdminManager {

    @Autowired
    public void setDao(DaoBaseImpl<Admin> adminDao) {
        setMainDao(adminDao);
    }

}
