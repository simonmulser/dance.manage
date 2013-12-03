package at.danceandfun.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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

    public List<Address> getStudioAddresses() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Address.class);

        Criterion rest1 = Restrictions.like("aid", 1);
        Criterion rest2 = Restrictions.like("aid", 2);
        criteria.add(Restrictions.or(rest1, rest2));

        criteria.add(Restrictions.eq("enabled", true));

        return mainDao.getListByCriteria(criteria);
    }
}
