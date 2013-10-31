package at.danceandfun.service;

import java.io.Serializable;
import java.util.List;

public interface ManagerBase<T> {

    public List<T> getList();

    public void save(T t);

    public void update(T t);

    public T get(Serializable id);
}
