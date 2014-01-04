package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Invoice;
import at.danceandfun.entity.Participant;
import at.danceandfun.service.InvoiceManager;
import at.danceandfun.service.ParticipantManager;

@Controller
@RequestMapping(value = "admin/invoice")
public class InvoiceController {

    private static Logger logger = Logger.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceManager invoiceManager;
    @Autowired
    private ParticipantManager participantManager;

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

    @RequestMapping(value = "/getParticipants", method = RequestMethod.GET)
    public @ResponseBody
    List<Participant> getParticipants(@RequestParam("term") String query) {
        logger.debug("Entered :" + query);

        List<Participant> participants = participantManager
                .searchForParticipants(query);
        List<Participant> participantsWithCourseDuration = new ArrayList<Participant>();
        for (Participant p : participants) {
            List<String> courseDurations = new ArrayList<String>();
            for (CourseParticipant cp : p.getCourseParticipants()) {
                courseDurations.add(cp.getKey().getCourse().getCid() + "/"
                        + cp.getKey().getCourse().getName() + ":"
                        + cp.getDuration() + "/"
                        + cp.getKey().getCourse().getSemesterPrice() + "/"
                        + cp.getKey().getCourse().getYearPrice());
            }
            p.setTempCourseDuration(courseDurations);
            participantsWithCourseDuration.add(p);
        }
        return participantsWithCourseDuration;
    }
}
