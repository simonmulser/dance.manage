package at.danceandfun.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import at.danceandfun.dao.DaoBase;

public abstract class ManagerBaseImpl<T> implements ManagerBase<T> {

    private DaoBase<T> dao;

    public void setDao(DaoBase<T> dao) {
        this.dao = dao;
    }

    @Override
    public List<T> getList() {
        return dao.getList();
    }

    @Override
    public void save(T t) {
        dao.save(t);
    }

    @Override
    public void update(T t) {
        dao.update(t);
    }

    @Override
    public T get(Serializable id) {
        return dao.get(id);
    }

    @Override
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria) {
        return dao.getListByCriteria(detachedCriteria);
    }

    @Override
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria,
            int offset, int size) {
        return dao.getListByCriteria(detachedCriteria, offset, size);
    }

}
