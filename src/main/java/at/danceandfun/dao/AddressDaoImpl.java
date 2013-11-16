package at.danceandfun.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Address;

@Repository
public class AddressDaoImpl extends DaoBaseImpl<Address> implements AddressDao {

    private static Logger logger = Logger.getLogger(AddressDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Address> getList() {
        logger.debug("loadAll");
        Criteria criteria = getCurrentSession().createCriteria(Address.class);
        criteria.add(Restrictions.eq("active", true));
        return criteria.list();
    }

}
