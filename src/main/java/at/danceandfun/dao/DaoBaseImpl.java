package at.danceandfun.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DaoBaseImpl<T> implements DaoBase<T> {
    private static Logger logger = Logger.getLogger(DaoBaseImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> classz;

    public DaoBaseImpl() {
    }

    public void persist(T domain) {
        logger.debug("save");
        entityManager.persist(domain);
    }

    public T merge(T domain) {
        logger.debug("update");
        return entityManager.merge(domain);
    }

    public T get(Serializable id) {
        logger.debug("get");
        return entityManager.find(classz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria,
            int offset, int size) {
        logger.debug("getListByCriteria with offset and size");
        return detachedCriteria.getExecutableCriteria(getHibernateSession())
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<T> getListByCriterions(List<Criterion> criterions) {
        logger.debug("getListByCriteria by criterions");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(classz);
        for (Criterion criterion : criterions) {
            detachedCriteria.add(criterion);
        }
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
    public List<T> getEnabledListWithCriteria(DetachedCriteria detachedCriteria) {
        logger.debug("getListByCriteria");
        detachedCriteria.add(Restrictions.eq("enabled", true));
        return detachedCriteria.getExecutableCriteria(getHibernateSession())
                .list();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getQueryResults(String query) {
        Session session = getHibernateSession();
        return session.createQuery(query).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getQueryResultsLong(String query) {
        Session session = getHibernateSession();
        return session.createQuery(query).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getQueryResultsCount(String query) {
        Session session = getHibernateSession();
        return ((Long) session.createQuery(query).uniqueResult()).intValue();
    }

    protected Session getHibernateSession() {
        Session session = entityManager.unwrap(Session.class);
        return session;
    }

    public Class<T> getClassz() {
        return classz;
    }

    public void setClassz(Class<T> classz) {
        this.classz = classz;
    }

    @SuppressWarnings("unchecked")
    public List<T> getEnabledList() {
        DetachedCriteria criteria = DetachedCriteria.forClass(classz);
        criteria.add(Restrictions.eq("enabled", true));
        return criteria.getExecutableCriteria(getHibernateSession()).list();
    }

    @Override
    public Class<T> getInjectedClass() {
        return classz;
    }
}
