package at.danceandfun.controller;

import java.util.List;

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

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Participant;
import at.danceandfun.service.AddressManager;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.ParticipantManager;

@Controller
@RequestMapping(value = "admin/participant")
public class EditParticipantController {

    private static Logger logger = Logger
            .getLogger(EditParticipantController.class);

    @Autowired
    private ParticipantManager participantManager;
    @Autowired
    private AddressManager addressManager;
    @Autowired
    private CourseManager courseManager;

    private Participant participant = new Participant();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listParticipants(ModelMap map) {
        logger.debug("LIST Participant with id " + participant.getPid());
        map.addAttribute("participant", participant);
        map.addAttribute("participantList", participantManager.getEnabledList());

        return "admin/editParticipantList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addParticipant(
            @ModelAttribute("participant") @Valid Participant participant,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.participant",
                    result);
            redirectAttributes.addFlashAttribute("participant", participant);
            this.participant = participant;
            return "redirect:/admin/participant";

        } else {
            logger.debug("ADD Participant with id " + participant.getPid());
            participant.setEnabled(true);

            if (!participant.getTempSiblings().equals("")) {
                String[] siblings = participant.getTempSiblings().split(";");
                for (String s : siblings) {
                    Participant actualParticipant = participantManager.get(Math
                            .abs(Integer.parseInt(s)));

                    if (Integer.parseInt(s) < 0) {
                        actualParticipant.getSiblings().remove(participant);
                        participant.getSiblings().remove(actualParticipant);
                        participantManager.update(participant);
                        participantManager.update(actualParticipant);
                    } else if (!participant.getSiblings().contains(
                            actualParticipant)) {
                        participant.getSiblings().add(actualParticipant);
                    }
                }
            }

            if (!participant.getTempCourses().equals("")) {
                String[] courses = participant.getTempCourses().split(";");
                for (String s : courses) {
                    Course actualCourse = courseManager.get(Math.abs(Integer
                            .parseInt(s)));

                    if (Integer.parseInt(s) < 0) {
                        // actualCourse.getParticipants().remove(participant);
                        participant.getCourses().remove(actualCourse);
                        participantManager.update(participant);
                        // courseManager.update(actualCourse);
                    } else if (!participant.getCourses().contains(actualCourse)) {
                        logger.debug("Neuen Kurs hinzufügen");
                        participant.getCourses().add(actualCourse);
                    } else {
                        logger.debug("Bestehender Kurs mit name: "
                                + actualCourse.getName());
                    }
                }
            }

            if (participant.getAddress().getAid() == null) {
                addressManager.save(participant.getAddress());
            }

            if (participant.getPid() == null) {
                logger.debug("New participant");
                participantManager.save(participant);
            } else {
                logger.debug("Update participant");
                participantManager.update(participant);
                logger.debug("Finished updating participant");
            }
            this.participant = new Participant();
        }
        return "redirect:/admin/participant";

    }

    @RequestMapping(value = "/edit/{pid}")
    public String editParticipant(@PathVariable("pid") Integer pid) {
        logger.debug("Edit Participant with id " + pid);
        participant = participantManager.get(pid);

        // TODO: getSiblings --> lazy Load?
        if (participantManager.get(pid).getSiblings().size() > 0) {
            String actualSiblings = "";
            for (Participant p : participant.getSiblings()) {
                actualSiblings += p.getPid().toString() + ";";
            }
            participant.setTempSiblings(actualSiblings);
        }

        if (participantManager.get(pid).getCourses().size() > 0) {
            String actualCourses = "";
            for (Course c : participant.getCourses()) {
                actualCourses += c.getCid().toString() + ";";
            }
            participant.setTempCourses(actualCourses);
        }

        return "redirect:/admin/participant";
    }

    @RequestMapping(value = "/delete/{pid}")
    public String deleteParticipant(@PathVariable("pid") Integer pid) {
        logger.debug("Delete Participant with id " + pid);
        participant = participantManager.get(pid);
        participant.setEnabled(false);
        participantManager.update(participant);
        participant = new Participant();
        return "redirect:/participant";
    }

    @RequestMapping(value = "/getSiblings", method = RequestMethod.GET)
    public @ResponseBody
    List getSiblings(@RequestParam("term") String query) {
        logger.debug("Entered :" + query);

        return participantManager.searchForSiblings(participant, query);
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    public @ResponseBody
    List getCourses(@RequestParam("term") String query) {
        logger.debug("Entered coursname:" + query);

        return courseManager.searchForCourses(participant, query);
    }

    public void setParticipantManager(ParticipantManager participantManager) {
        this.participantManager = participantManager;
    }

    public void setAddressManager(AddressManager addressManager) {
        this.addressManager = addressManager;
    }

}
