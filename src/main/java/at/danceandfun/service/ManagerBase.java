package at.danceandfun.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface ManagerBase<T> {

    public void persist(T domain);

    public boolean contains(T domain);

    public T merge(T domain);

    public T get(Serializable id);

    public List<T> getEnabledListWithCriteria(DetachedCriteria detachedCriteria);

    public List<T> getEnabledList();

    public List<T> getListByCriteria(DetachedCriteria detachedCriteria);

    public List<T> getListByCriteria(DetachedCriteria detachedCriteria,
            int offset, int size);

    public List<T> getQueryResults(String query);
}
