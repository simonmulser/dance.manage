package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Style;

@Service
public class StyleManagerImpl extends ManagerBaseImpl<Style> implements
        StyleManager {

    @Autowired
    public void setDao(DaoBaseImpl<Style> styleDao) {
        setMainDao(styleDao);
    }

}
