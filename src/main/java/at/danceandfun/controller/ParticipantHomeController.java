package at.danceandfun.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.Participant;
import at.danceandfun.service.ParticipantManager;

@Controller
@RequestMapping(value = "/participant")
public class ParticipantHomeController {

    private static Logger logger = Logger
            .getLogger(ParticipantHomeController.class);

    @Autowired
    private ParticipantManager participantManager;

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
        participantManager.update(participant);
        return "redirect:/participant";

    }

    private Participant getLoggedInParticipant() {
        // TODO merge with session no plain get
        Participant participant = (Participant) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return participantManager.get(participant.getPid());
    }
}
