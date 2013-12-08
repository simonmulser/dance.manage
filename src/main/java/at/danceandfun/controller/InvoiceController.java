package at.danceandfun.controller;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.entity.Invoice;
import at.danceandfun.service.InvoiceManager;

@Controller
@RequestMapping(value = "admin/invoice")
public class InvoiceController {

    private static Logger logger = Logger.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceManager invoiceManager;

    private Invoice invoice;

    @PostConstruct
    public void init() {
        invoice = new Invoice();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listInvoices(ModelMap map) {
        logger.debug("LIST all invoices from DB");

        map.addAttribute("invoice", invoice);
        map.addAttribute("invoiceList", invoiceManager.getEnabledList());
        return "admin/invoiceView";
    }
}
