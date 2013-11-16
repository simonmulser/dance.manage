package at.danceandfun.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.InvoiceDao;
import at.danceandfun.entity.Invoice;

@Service
public class InvoiceManagerImpl extends ManagerBaseImpl<Invoice> implements
        InvoiceManager {

    private InvoiceDao invoiceDao;

    @Autowired
    public void initializeDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
        setDao(invoiceDao);
    }

    public List<Invoice> getActiveList() {
        DetachedCriteria active = DetachedCriteria.forClass(Invoice.class);
        active.add(Restrictions.eq("active", true));
        return invoiceDao.getListByCriteria(active);
    }

}
