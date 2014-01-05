package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Invoice;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Position;
import at.danceandfun.entity.PositionID;
import at.danceandfun.enumeration.Duration;
import at.danceandfun.service.CourseParticipantManager;
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
    @Autowired
    private CourseParticipantManager courseParticipantManager;

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

        return participantManager.searchForParticipants(query);
    }

    @RequestMapping(value = "/getDetailsForParticipant/{pid}")
    public String detailsForParticipant(@PathVariable("pid") Integer pid,
            ModelMap map) {
        invoice = new Invoice();
        Participant actualParticipant = participantManager.get(pid);
        for (CourseParticipant cp : actualParticipant.getCourseParticipants()) {
            int courseCount = courseParticipantManager
                    .getCourseCountByParticipant(cp.getCourse().getCid(),
                            actualParticipant.getPid());
            if (cp.isEnabled() && cp.getDuration() != Duration.YEAR
                    && courseCount < 2) {
                Position pos = new Position();
                PositionID posID = new PositionID();
                posID.setCourse(cp.getCourse());
                posID.setInvoice(invoice);
                pos.setKey(posID);
                invoice.getPositions().add(pos);
            }
        }
        invoice.setParticipant(actualParticipant);
        if (actualParticipant.hasParent()) {
            invoice.setParent(actualParticipant.getParent());
        }

        return "redirect:/admin/invoice";
    }

    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public String previewInvoice(
            @ModelAttribute(value = "invoice") @Valid Invoice invoice,
            BindingResult result, RedirectAttributes redirectAttributes,
            ModelMap map) {

        Participant actualParticipant = participantManager.get(invoice
                .getParticipant().getPid());
        invoice.setParticipant(actualParticipant);
        if (actualParticipant.hasParent()) {
            invoice.setParent(actualParticipant.getParent());
        }
        logger.debug("PREVIEW OF INVOICE FOR "
                + actualParticipant.getFirstname());

        boolean readyToSave = true;
        List<Position> positionsWithErrors = new ArrayList<Position>();
        double totalAmount = 0;
        for (CourseParticipant cp : actualParticipant.getCourseParticipants()) {
            int courseCount = courseParticipantManager
                    .getCourseCountByParticipant(cp.getCourse().getCid(),
                            actualParticipant.getPid());
            for (Position pos : invoice.getPositions()) {
                if (cp.isEnabled()
                        && cp.getCourse().getCid() == pos.getKey().getCourse()
                                .getCid()) {
                    if (pos.getDuration() == cp.getDuration()
                            && pos.getDuration() != Duration.NONE) {
                        pos.setErrorMessage("Für "
                                + pos.getKey().getCourse().getName()
                                + " existiert bereits eine Rechnung.");
                        readyToSave = false;
                    } else if (pos.getDuration() == Duration.YEAR
                            && courseCount != 0) {
                        pos.setErrorMessage("Für "
                                + pos.getKey().getCourse().getName()
                                + " wurde bereits 1 Semester bezahlt.");
                        readyToSave = false;
                    } else if (pos.getDuration() == Duration.NONE) {
                        pos.setErrorMessage("Diese Position wird nicht berücksichtigt.");
                    } else {
                        if (pos.getDuration() == Duration.YEAR) {
                            pos.setAmount(cp.getCourse().getYearPrice());
                        } else {
                            pos.setAmount(cp.getCourse().getSemesterPrice());
                        }
                        pos.setErrorMessage("Position in Ordnung.");
                        totalAmount += pos.getAmount();
                    }
                    positionsWithErrors.add(pos);
                }
            }

        }

        invoice.setPositions(positionsWithErrors);
        if (readyToSave) {
            if (invoice.getReduction() != null) {
                invoice.setTotalAmount(totalAmount
                        - (totalAmount * (invoice.getReduction() / 100)));
            } else {
                invoice.setTotalAmount(totalAmount);
            }

        }
        this.invoice = invoice;
        map.addAttribute("readyToSave", readyToSave);
        return "redirect:/admin/invoice";
    }
}
