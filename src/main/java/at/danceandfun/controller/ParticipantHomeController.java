package at.danceandfun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import at.danceandfun.entity.Rating;
import at.danceandfun.service.AppointmentManager;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.CourseParticipantManager;
import at.danceandfun.service.ParticipantManager;
import at.danceandfun.service.RatingManager;

@Controller
@RequestMapping(value = "/participant")
@PreAuthorize("hasPermission(#pid, 'owner') or hasPermission(#pid, 'isParent')")
public class ParticipantHomeController {

    private static Logger logger = Logger
            .getLogger(ParticipantHomeController.class);

    @Autowired
    private ParticipantManager participantManager;

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private CourseParticipantManager courseParticipantManager;

    @Autowired
    private AppointmentManager appointmentManager;

    @Autowired
    private RatingManager ratingManager;

    private Rating rating;

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public String showIndex(ModelMap map, @PathVariable int pid) {
        logger.debug("showIndex");
        Participant participant = participantManager.get(pid);

        map.put("participant", participant);
        return "participant/index";
    }

    @RequestMapping(value = "/edit/{pid}", method = RequestMethod.GET)
    public String showEdit(ModelMap map, @PathVariable int pid) {
        logger.debug("showEdit");
        Participant participant = participantManager.get(pid);

        map.put("participant", participant);
        return "participant/editParticipant";
    }

    @RequestMapping(value = "/edit/{pid}", method = RequestMethod.POST)
    public String updateParticipant(ModelMap map,
            @ModelAttribute("participant") @Valid Participant participant,
            BindingResult result, RedirectAttributes redirectAttributes,
            @PathVariable int pid) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.participant",
                    result);
            redirectAttributes.addFlashAttribute("participant", participant);
            return "redirect:/participant/" + pid;
        }

        logger.debug("updateParticipant");
        participantManager.merge(participant);
        return "redirect:/participant/" + pid;

    }

    @RequestMapping(value = "/absence/{pid}", method = RequestMethod.GET)
    public String showAbsence(ModelMap map, @PathVariable int pid) {
        logger.debug("showAbsence");
        Participant participant = participantManager.get(pid);

        map.put("participant", participant);
        return "participant/absenceView";
    }

    @RequestMapping(value = "/absence/save/{slug}/{pid}", method = RequestMethod.POST)
    public String saveAbsence(@PathVariable String slug, @PathVariable int pid,
            HttpServletRequest request, ModelMap map) {
        Participant participant = participantManager.get(pid);

        String reason = request.getParameter("reason");
        Integer appointmentId = Integer.parseInt(request
                .getParameter("appointmentId"));

        Appointment appointment = appointmentManager.get(appointmentId);
        Absence absence = new Absence(participant, appointment, reason);

        logger.debug("saveAbsence for course:" + slug + " appointment("
                + appointment + ") cause:" + reason);

        participantManager.mergeAbsence(absence);

        map.put("participant", participant);
        return "participant/absenceView";
    }

    @RequestMapping(value = "/absence/update/{slug}/{pid}", method = RequestMethod.POST)
    public String deleteAbsence(@PathVariable String slug,
            @PathVariable int pid, HttpServletRequest request, ModelMap map) {
        Participant participant = participantManager.get(pid);
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

        map.put("participant", participant);
        return "participant/absenceView";
    }

    @RequestMapping(value = "/rating/{pid}", method = RequestMethod.GET)
    public String showRating(ModelMap map, @PathVariable int pid) {
        logger.debug("showRating");
        Participant participant = participantManager.get(pid);
        participant.setCourseParticipants(courseParticipantManager
                .getEnabeledCourseParticipants(participant));
        this.rating = new Rating();
        map.put("rating", this.rating);
        map.addAttribute("ratingList",
                ratingManager.getEnabledRatings(participant));
        map.put("participant", participant);
        return "participant/ratingView";
    }

    @RequestMapping(value = "/rating/add/{pid}", method = RequestMethod.POST)
    public String addRating(ModelMap map, @PathVariable int pid,
            @ModelAttribute(value = "rating") @Valid Rating rating) {
        logger.debug("addRating");
        Participant participant = participantManager.get(pid);
        rating.setEnabled(true);
        this.rating = rating;
        ratingManager.persist(rating);
        rating = new Rating();
        map.put("rating", rating);
        map.put("participant", participant);
        return "redirect:/participant/rating/" + pid;
    }

    @RequestMapping(value = "rating/delete/{pid}/{rid}")
    public String deleteRating(ModelMap map, @PathVariable("rid") Integer rid,
            @PathVariable("pid") Integer pid) {
        rating = ratingManager.get(rid);
        Participant participant = participantManager.get(pid);
        rating.setEnabled(false);
        ratingManager.merge(rating);
        map.put("participant", participant);
        return "redirect:/participant/rating/" + pid;
    }

    private Participant getLoggedInParticipant() {
        Participant participant = (Participant) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return participantManager.get(participant.getPid());
    }
}
