package at.danceandfun.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface DaoBase<T> {

    public List<T> getList();

    public void save(T domain);

    public void update(T domain);

    public T get(Serializable id);

    public List<T> getListByCriteria(DetachedCriteria detachedCriteria);

    public List<T> getListByCriteria(DetachedCriteria detachedCriteria,
            int offset, int size);
}
