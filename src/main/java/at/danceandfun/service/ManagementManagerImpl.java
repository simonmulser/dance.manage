package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.ManagementDao;
import at.danceandfun.entity.Management;

@Service
public class ManagementManagerImpl extends ManagerBaseImpl<Management>
        implements ManagementManager {

    private ManagementDao managementDao;

    @Autowired
    public void initializeDao(ManagementDao managementDao) {
        this.managementDao = managementDao;
        setDao(managementDao);
    }
}
