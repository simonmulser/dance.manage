package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.AddressDao;
import at.danceandfun.entity.Address;

@Service
public class AddressManagerImpl extends ManagerBaseImpl<Address> implements
        AddressManager {

    public AddressManagerImpl() {
        super(Address.class);
        // TODO Auto-generated constructor stub
    }

    @Autowired
    public void initializeDao(AddressDao addresstDao) {
        setDao(addresstDao);
    }
}
