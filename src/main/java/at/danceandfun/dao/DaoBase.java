package at.danceandfun.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

public interface DaoBase<T> {
    /*
     * Make an instance managed and persistent.
     */
    public void persist(T domain);

    /*
     * Merge the state of the given entity into the current persistence context.
     * Returns the managed instance that the state was merged to.
     */
    public T merge(T domain);

    public T get(Serializable id);

    public List<T> getEnabledListWithCriteria(DetachedCriteria detachedCriteria);

    public List<T> getListByCriteria(DetachedCriteria detachedCriteria);

    public List<T> getListByCriteria(DetachedCriteria detachedCriteria,
            int offset, int size);

    public List<T> getQueryResults(String query);

    public List<Long> getQueryResultsLong(String query);

    public List<T> getListByCriterions(List<Criterion> criterions);

    public Class<T> getInjectedClass();

}
