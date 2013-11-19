package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.ParentDao;
import at.danceandfun.entity.Parent;

@Service
public class ParentManagerImpl extends ManagerBaseImpl<Parent> implements
        ParentManager {

    private ParentDao parentDao;

    public ParentManagerImpl() {
        super(Parent.class);
        // TODO Auto-generated constructor stub
    }

    @Autowired
    public void initializeDao(ParentDao parentDao) {
        this.parentDao = parentDao;
        setDao(parentDao);
    }
}
