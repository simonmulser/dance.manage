package at.danceandfun.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.entity.Participant;
import at.danceandfun.service.ParticipantManager;

@Controller
@RequestMapping(value = "/participant")
public class EditParticipantController {

    private static Logger logger = Logger
            .getLogger(EditParticipantController.class);

    @Autowired
    private ParticipantManager participantManager;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listParticipants(ModelMap map) {
        map.addAttribute("participant", new Participant());
        map.addAttribute("participantList", participantManager.getList());

        logger.error("FIRSTNAME: "
                + participantManager.getList().get(0).getFirstname());

        return "editParticipantList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addParticipant(
            @ModelAttribute(value = "participant") Participant participant,
            BindingResult result) {
        participantManager.save(participant);
        return "redirect:/participant";
    }

    public void setParticipantManager(ParticipantManager participantManager) {
        this.participantManager = participantManager;
    }

}
