package at.danceandfun.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import at.danceandfun.dao.DaoBaseImpl;

public abstract class ManagerBaseImpl<T> implements ManagerBase<T> {

    protected DaoBaseImpl<T> mainDao;

    @Override
    public void persist(T domain) {
        mainDao.persist(domain);

    }

    @Override
    public boolean contains(T domain) {
        return mainDao.contains(domain);
    }

    @Override
    public T merge(T domain) {
        return mainDao.merge(domain);
    }

    @Override
    public T get(Serializable id) {
        return mainDao.get(id);
    }

    @Override
    public List<T> getEnabledListWithCriteria(DetachedCriteria detachedCriteria) {
        return mainDao.getEnabledListWithCriteria(detachedCriteria);
    }

    @Override
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria) {
        return mainDao.getListByCriteria(detachedCriteria);
    }

    @Override
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria,
            int offset, int size) {
        return mainDao.getListByCriteria(detachedCriteria, offset, size);
    }

    @Override
    public List<T> getQueryResults(String query) {
        return mainDao.getQueryResults(query);
    }

    @Override
    public List<T> getEnabledList() {
        return mainDao.getEnabledList();
    }

    public DaoBaseImpl<T> getMainDao() {
        return mainDao;
    }

    public void setMainDao(DaoBaseImpl<T> mainDao) {
        this.mainDao = mainDao;
    }
}
