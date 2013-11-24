package at.danceandfun.controller;

import java.util.List;

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

        /*
         * if (participant.getPid()) { map.addAttribute("participantSiblings",
         * participant.getSiblings()); }
         */

        return "editParticipantList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addParticipant(
            @ModelAttribute(value = "participant") Participant participant,
            BindingResult result) {
        logger.debug("ADD Participant with id " + participant.getPid());
        logger.debug("ADD Participant with bd " + participant.getBirthday());
        participant.setEnabled(true);

        // TODO is not saving the participant enough?

        if (participant.getSiblings().isEmpty()) {
            logger.debug("Has siblings");
        }

        /*
         * if (!(participant.getAddress() == null)) {
         * logger.debug("ADD Participant with address: " +
         * participant.getAddress().getAid().toString());
         * addressManager.save(participant.getAddress()); }
         */

        participantManager.save(participant);

        this.participant = new Participant();
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
    public @ResponseBody
    List<String> getSiblings(@RequestParam("term") String query) {
        logger.debug("Entered :" + query);

        return participantManager.searchForSiblings(query);
    }

    public void setParticipantManager(ParticipantManager participantManager) {
        this.participantManager = participantManager;
    }

    public void setAddressManager(AddressManager addressManager) {
        this.addressManager = addressManager;
    }

}
