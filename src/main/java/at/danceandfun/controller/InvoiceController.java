package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import at.danceandfun.util.AppContext;

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
    private int status; // 0=preview, 1=readyToSave, 2=Save, 3=Saved
    private boolean editTrue = false;

    @PostConstruct
    public void init() {
        invoice = new Invoice();
        status = 0;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listInvoices(ModelMap map) {
        logger.debug("LIST all invoices from DB");

        if (status == 3) {
            init();
        }

        if (!editTrue) {
            invoice = new Invoice();
        }

        map.addAttribute("invoice", invoice);
        map.addAttribute("invoiceList", invoiceManager.getEnabledList());
        map.addAttribute("status", status);
        editTrue = false;
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
        editTrue = true;
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

        editTrue = true;
        Participant actualParticipant = participantManager.get(invoice
                .getParticipant().getPid());
        invoice.setParticipant(actualParticipant);
        if (actualParticipant.hasParent()) {
            invoice.setParent(actualParticipant.getParent());
        }
        logger.debug("PREVIEW OF INVOICE FOR "
                + actualParticipant.getFirstname());

        if (status == 0) {
            status = 1;
        }

        List<Position> positionsWithErrors = new ArrayList<Position>();
        double totalAmount = 0;
        int noneCounts = 0;
        for (CourseParticipant cp : actualParticipant.getCourseParticipants()) {
            int courseCount = courseParticipantManager
                    .getCourseCountByParticipant(cp.getCourse().getCid(),
                            actualParticipant.getPid());
            for (Position pos : invoice.getPositions()) {
                if (cp.isEnabled()
                        && cp.getCourse().getCid() == pos.getKey().getCourse()
                                .getCid()) {
                    if (pos.getDuration() == cp.getDuration()) {
                        pos.setErrorMessage(geti18nMessage("message.invoiceExisting"));
                        status = 0;
                    } else if (pos.getDuration() == Duration.YEAR
                            && courseCount != 0) {
                        pos.setErrorMessage(geti18nMessage("message.invoiceOneSemester"));
                        status = 0; // preview
                    } else if (pos.getDuration() == Duration.NONE) {
                        pos.setErrorMessage(geti18nMessage("message.notRelevatPosition"));
                        noneCounts++;
                    } else {
                        if (pos.getDuration() == Duration.YEAR) {
                            pos.setAmount(cp.getCourse().getYearPrice());
                        } else {
                            pos.setAmount(cp.getCourse().getSemesterPrice());
                        }
                        pos.setErrorMessage(geti18nMessage("message.okPosition"));
                        totalAmount += pos.getAmount();
                    }
                    positionsWithErrors.add(pos);
                }
            }

        }

        invoice.setPositions(positionsWithErrors);
        if (noneCounts == invoice.getPositions().size()) {
            status = 0;
        }
        if (status != 0) {
            if (invoice.getReduction() != null) {
                invoice.setTotalAmount(totalAmount
                        - (totalAmount * (invoice.getReduction() / 100)));
            } else {
                invoice.setTotalAmount(totalAmount);
            }
        }
        if (status == 1) { // readyToSave
            status = 2;

        } else if (status == 2) { // save
            List<Position> finalPositions = new ArrayList<Position>();
            for (Position pos : invoice.getPositions()) {
                if (pos.getDuration() != Duration.NONE) {
                    pos.getKey().setInvoice(invoice);
                    finalPositions.add(pos);
                }
            }
            invoice.setPositions(finalPositions);
            invoice.setEnabled(true);
            invoice.setDate(new LocalDateTime());
            invoiceManager.persist(invoice);
            logger.debug("SAVED");
            status = 3;
        }
        this.invoice = invoice;
        return "redirect:/admin/invoice";
    }

    @RequestMapping(value = "/delete/{iid}")
    public String deleteInvoice(@PathVariable("iid") Integer iid) {
        logger.debug("Delete Invoice with id " + iid);
        invoice = invoiceManager.get(iid);
        if (Period.fieldDifference(new LocalDateTime(), invoice.getDate())
                .getMonths() > 3) {
            logger.debug("Couldn't be canceled");
        } else {

            invoice.setEnabled(false);
            invoiceManager.merge(invoice);

        }
        invoice = new Invoice();
        return "redirect:/admin/invoice";
    }

    public String geti18nMessage(String identifier) {
        Locale locale = LocaleContextHolder.getLocale();
        return AppContext.getApplicationContext().getMessage(identifier, null,
                locale);
    }
}
