package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Invoice;

public interface InvoiceManager extends ManagerBase<Invoice> {

    public List<Invoice> getActiveList();
}
