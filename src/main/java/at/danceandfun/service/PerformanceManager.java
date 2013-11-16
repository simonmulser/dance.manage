package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Performance;

public interface PerformanceManager extends ManagerBase<Performance> {

    public List<Performance> getActiveList();
}
