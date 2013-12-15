package at.danceandfun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.Absence;
import at.danceandfun.entity.Appointment;
import at.danceandfun.entity.Participant;
import at.danceandfun.service.AppointmentManager;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.ParticipantManager;

@Controller
@RequestMapping(value = "/participant")
public class ParticipantHomeController {

    private static Logger logger = Logger
            .getLogger(ParticipantHomeController.class);

    @Autowired
    private ParticipantManager participantManager;

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private AppointmentManager appointmentManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showIndex(ModelMap map) {
        logger.debug("showIndex");
        Participant participant = getLoggedInParticipant();

        map.put("user", participant);
        return "participant/index";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showEdit(ModelMap map) {
        logger.debug("showEdit");
        Participant participant = getLoggedInParticipant();

        map.put("participant", participant);
        return "participant/editParticipant";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateParticipant(ModelMap map,
            @ModelAttribute("participant") @Valid Participant participant,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.participant",
                    result);
            redirectAttributes.addFlashAttribute("participant", participant);
            return "redirect:/participant/edit";
        }

        logger.debug("updateParticipant");
        participantManager.merge(participant);
        return "redirect:/participant";

    }

    @RequestMapping(value = "/absence", method = RequestMethod.GET)
    public String showAbsence(ModelMap map) {
        logger.debug("showAbsence");
        Participant participant = getLoggedInParticipant();

        map.put("user", participant);
        return "participant/absenceView";
    }

    @RequestMapping(value = "/absence/save/{slug}", method = RequestMethod.POST)
    public String saveAbsence(@PathVariable String slug,
            HttpServletRequest request, ModelMap map) {
        Participant participant = getLoggedInParticipant();
        String reason = request.getParameter("reason");
        Integer appointmentId = Integer.parseInt(request
                .getParameter("appointmentId"));

        Appointment appointment = appointmentManager.get(appointmentId);
        Absence absence = new Absence(participant, appointment, reason);

        logger.debug("saveAbsence for course:" + slug + " appointment("
                + appointment + ") cause:" + reason);

        participantManager.mergeAbsence(absence);

        map.put("user", participant);
        return "participant/absenceView";
    }

    @RequestMapping(value = "/absence/update/{slug}", method = RequestMethod.POST)
    public String deleteAbsence(@PathVariable String slug,
            HttpServletRequest request, ModelMap map) {
        Participant participant = getLoggedInParticipant();
        Integer appointmentId = Integer.parseInt(request
                .getParameter("appointmentId"));
        String enabled = request.getParameter("enabled");
        String reason = request.getParameter("reason");

        Appointment appointment = appointmentManager.get(appointmentId);
        Absence absence = new Absence(participant, appointment, reason);

        if ("false".equals(enabled)) {
            absence.setEnabled(false);
        } else {
            absence.setEnabled(true);
        }

        logger.debug("updateAbsence for course:" + slug + " appointment:"
                + appointment + " participant:" + participant);

        participantManager.mergeAbsence(absence);

        map.put("user", participant);
        return "participant/absenceView";
    }

    private Participant getLoggedInParticipant() {
        Participant participant = (Participant) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return participantManager.get(participant.getPid());
    }
}
