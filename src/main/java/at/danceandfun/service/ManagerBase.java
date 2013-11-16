package at.danceandfun.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface ManagerBase<T> {

    public List<T> getList();

    public void save(T t);

    public void update(T t);

    public T get(Serializable id);

    public List<T> getListByCriteria(DetachedCriteria detachedCriteria);

    public List<T> getListByCriteria(DetachedCriteria detachedCriteria,
            int offset, int size);
}
