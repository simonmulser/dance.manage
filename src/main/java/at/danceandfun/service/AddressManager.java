package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Address;

public interface AddressManager extends ManagerBase<Address> {

    public List<Address> getStudioAddresses();
}
