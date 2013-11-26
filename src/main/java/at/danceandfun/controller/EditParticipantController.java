package at.danceandfun.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.Participant;
import at.danceandfun.service.AddressManager;
import at.danceandfun.service.ParticipantManager;

@Controller
@RequestMapping(value = "/participant")
public class EditParticipantController {

    private static Logger logger = Logger
            .getLogger(EditParticipantController.class);

    @Autowired
    private ParticipantManager participantManager;
    @Autowired
    private AddressManager addressManager;

    private Participant participant = new Participant();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listParticipants(ModelMap map) {
        logger.debug("LIST Participant with id " + participant.getPid());
        map.addAttribute("participant", participant);
        map.addAttribute("participantList", participantManager.getEnabledList());

        return "editParticipantList";
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
            return "redirect:/participant";
        } else {
            logger.debug("ADD Participant with id " + participant.getPid());
            participant.setEnabled(true);

            // TODO is not saving the participant enough?

            participantManager.save(participant);
            if (!participant.getAddress().equals(null)) {
                addressManager.save(participant.getAddress());
            }

            this.participant = new Participant();
        }
        return "redirect:/participant";


    }

    @RequestMapping(value = "/edit/{pid}")
    public String editParticipant(@PathVariable("pid") Integer pid) {
        logger.debug("Edit Participant with id " + pid);
        participant = participantManager.get(pid);
        return "redirect:/participant";
    }

    @RequestMapping(value = "/delete/{pid}")
    public String deleteParticipant(@PathVariable("pid") Integer pid) {
        logger.debug("Delete Participant with id " + pid);
        participant = participantManager.get(pid);
        participant.setEnabled(false);
        participantManager.update(participant);
        // p.getAddress().setEnabled(false);
        // addressManager.update(p.getAddress());
        participant = new Participant();
        return "redirect:/participant";
    }

    @RequestMapping(value = "/getSiblings", method = RequestMethod.GET)
    public String getSiblings(ModelMap map) {
        logger.debug("LIST Participant with id " + participant.getPid());
        map.addAttribute("participant", participant);
        map.addAttribute("participantList", participantManager.getEnabledList());

        return "editParticipantList";
    }

    public void setParticipantManager(ParticipantManager participantManager) {
        this.participantManager = participantManager;
    }

}
