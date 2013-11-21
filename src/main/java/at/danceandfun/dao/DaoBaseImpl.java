package at.danceandfun.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public abstract class DaoBaseImpl<T> implements DaoBase<T> {
    private static Logger logger = Logger.getLogger(DaoBaseImpl.class);

    private Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public DaoBaseImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void save(T domain) {
        logger.debug("save");
        entityManager.persist(domain);
    }

    public void update(T domain) {
        logger.debug("update");
        entityManager.merge(domain);
    }

    public T get(Serializable id) {
        logger.debug("get");
        return entityManager.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria,
            int offset, int size) {
        logger.debug("getListByCriteria with offset and size");
        return detachedCriteria.getExecutableCriteria(getHibernateSession())
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria) {
        logger.debug("getListByCriteria");
        return detachedCriteria.getExecutableCriteria(getHibernateSession())
                .list();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getQueryResults(String query) {
        Session session = getHibernateSession();
        return session.createQuery(query).list();
    }

    protected Session getHibernateSession() {
        Session session = entityManager.unwrap(Session.class);
        return session;
    }
}
