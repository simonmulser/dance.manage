package at.danceandfun.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Address;

@Service
public class AddressManagerImpl extends ManagerBaseImpl<Address> implements
        AddressManager {

    private static Logger logger = Logger.getLogger(AddressManagerImpl.class);

    @Autowired
    public void setDao(DaoBaseImpl<Address> addressDao) {
        setMainDao(addressDao);
    }
}
