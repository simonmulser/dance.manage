package at.danceandfun.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Invoice;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class InvoiceDaoTest {

    @Autowired
    private DaoBaseImpl<Invoice> invoiceDao;

    public static Invoice getValidInvoice() {
        Invoice invoice = new Invoice();
        invoice.setParticipant(ParticipantDaoTest.getValidParticipant());
        invoice.setDate(new LocalDateTime());
        invoice.setEnabled(true);
        return invoice;
    }

    @Test
    public void testSave() {
        invoiceDao.persist(getValidInvoice());
    }

    @Test
    public void testUpdate() {
        Invoice invoice = invoiceDao.get(1);
        if (invoice != null) {
            invoiceDao.merge(invoice);
        } else {
            fail("database is empty");
        }
    }

    @Test
    public void testGetInvoicePositions() {
        Invoice invoice = invoiceDao.get(1);
        if (invoice != null) {
            assertThat(invoice.getPositions().isEmpty(), is(false));
        } else {
            fail("database is empty");
        }
    }
}
