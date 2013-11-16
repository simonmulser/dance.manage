package at.danceandfun.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class DaoBaseImpl<T> implements DaoBase<T> {
    private static Logger logger = Logger.getLogger(DaoBaseImpl.class);

    private Class<T> entityClass;

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public DaoBaseImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public List<T> getList() {
        logger.debug("loadAll");
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        return criteria.list();
    }

    public void save(T domain) {
        logger.debug("save");
        getCurrentSession().saveOrUpdate(domain);

    }

    public void update(T domain) {
        logger.debug("update");
        getCurrentSession().merge(domain);
    }

    public T get(Serializable id) {
        logger.debug("get");
        @SuppressWarnings("unchecked")
        T object = (T) getCurrentSession().get(entityClass, id);
        return object;
    }

    @SuppressWarnings("unchecked")
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria,
            int offset, int size) {
        logger.debug("getListByCriteria with offset and size");
        return detachedCriteria.getExecutableCriteria(getCurrentSession())
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria) {
        logger.debug("getListByCriteria");

        return detachedCriteria.getExecutableCriteria(getCurrentSession())
                .list();

    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
