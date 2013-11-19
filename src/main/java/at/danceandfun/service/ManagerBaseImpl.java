package at.danceandfun.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import at.danceandfun.dao.DaoBase;

public abstract class ManagerBaseImpl<T> implements ManagerBase<T> {

    protected DaoBase<T> dao;

    private final Class<T> type;

    public ManagerBaseImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public void setDao(DaoBase<T> dao) {
        this.dao = dao;
    }

    @Override
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria) {
        return dao.getListByCriteria(detachedCriteria);
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
    public List<T> getListByCriteria(DetachedCriteria detachedCriteria,
            int offset, int size) {
        return dao.getListByCriteria(detachedCriteria, offset, size);
    }

    @Override
    public List<T> getActiveList() {
        DetachedCriteria criteria = DetachedCriteria.forClass(this.type);
        criteria.add(Restrictions.eq("active", true));
        return dao.getListByCriteria(criteria);
    }
}
