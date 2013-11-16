package at.danceandfun.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import at.danceandfun.entity.Invoice;

@Repository
public class InvoiceDaoImpl extends DaoBaseImpl<Invoice> implements InvoiceDao {

    private static Logger logger = Logger.getLogger(InvoiceDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

}
