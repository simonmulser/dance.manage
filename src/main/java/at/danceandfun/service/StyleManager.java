package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Style;

public interface StyleManager extends ManagerBase<Style> {

    public List<Style> getActiveList();
}
