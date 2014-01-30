package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
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
        logger.info("LIST all invoices from DB");
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
                List<Duration> possibleDurations = new ArrayList<Duration>();
                if (cp.getDuration() == Duration.NONE) {
                    possibleDurations = Arrays.asList(Duration.values());
                } else if (cp.getDuration() == Duration.SUMMER) {
                    possibleDurations.add(Duration.WINTER);
                    possibleDurations.add(Duration.NONE);
                } else if (cp.getDuration() == Duration.WINTER) {
                    possibleDurations.add(Duration.SUMMER);
                    possibleDurations.add(Duration.NONE);
                }
                pos.setPossibleDurations(possibleDurations);
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
        if (invoice.getParticipant() != null) {
            editTrue = true;
            Participant actualParticipant = participantManager.get(invoice
                    .getParticipant().getPid());
            invoice.setParticipant(actualParticipant);
            if (actualParticipant.hasParent()) {
                invoice.setParent(actualParticipant.getParent());
            }
            if (status == 0) {
                status = 1;
            }

            List<Position> positionsWithErrors = new ArrayList<Position>();
            List<CourseParticipant> courseParticipantsToUpdate = new ArrayList<CourseParticipant>();
            double totalAmount = 0;
            int noneCounts = 0;
            for (CourseParticipant cp : actualParticipant
                    .getCourseParticipants()) {
                for (Position pos : invoice.getPositions()) {
                    if (cp.isEnabled()
                            && cp.getCourse().getCid() == pos.getKey()
                                    .getCourse().getCid()) {
                        if (pos.getDuration() == Duration.NONE) {
                            pos.setErrorMessage(geti18nMessage("message.notRelevatPosition"));
                            noneCounts++;
                        } else {
                            CourseParticipant tempCP = new CourseParticipant();
                            tempCP.setCourse(cp.getCourse());
                            tempCP.setParticipant(cp.getParticipant());
                            tempCP.setEnabled(true);
                            tempCP.setDuration(pos.getDuration());
                            courseParticipantsToUpdate.add(tempCP);
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

            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute(
                        "org.springframework.validation.BindingResult.invoice",
                        result);
                redirectAttributes.addFlashAttribute("invoice", invoice);
                this.invoice = invoice;
                editTrue = true;
                status = 0;
                return "redirect:/admin/invoice";

            }

            if (noneCounts == invoice.getPositions().size()) { // alle Pos NONE
                status = 0;
            }
            if (status != 0) {
                if (invoice.getReduction() != null) {
                    invoice.setReductionAmount(totalAmount
                            * (invoice.getReduction() / 100));
                    invoice.setTotalAmount(totalAmount
                            - invoice.getReductionAmount());
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
                for (CourseParticipant cp : courseParticipantsToUpdate) {
                    courseParticipantManager.persist(cp);
                }
                status = 3;
            }
            this.invoice = invoice;
        }
        return "redirect:/admin/invoice";
    }

    public String geti18nMessage(String identifier) {
        Locale locale = LocaleContextHolder.getLocale();
        return AppContext.getApplicationContext().getMessage(identifier, null,
                locale);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @RequestMapping(value = "/viewInvoicePDF/{iid}", method = RequestMethod.GET)
    public ModelAndView viewInvoicePdf(@PathVariable("iid") Integer iid) {
        logger.info("Creating pdf for invoice with id " + iid);
        Invoice inv = invoiceManager.get(iid);
        if (inv == null) {
            throw new IllegalArgumentException("Invoice with id " + iid
                    + " not found.");
        }
        return new ModelAndView("viewInvoicePdf", "invoice",
                invoiceManager.get(iid));
    }
}
