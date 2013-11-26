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

            if (!participant.getTempSiblings().equals("")) {
                String[] siblings = participant.getTempSiblings().split(";");
                for (String s : siblings) {
                    Participant actualParticipant = participantManager.get(Math
                            .abs(Integer.parseInt(s)));

                    if (Integer.parseInt(s) < 0) {
                        actualParticipant.getSiblings().remove(participant);
                        participant.getSiblings().remove(actualParticipant);
                        logger.debug("Kann ich den Sibling speichern?");
                        participantManager.update(actualParticipant);
                    } else {
                        participant.getSiblings().add(actualParticipant);
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
                logger.debug("His address: "
                        + participant.getAddress().getAid() + " "
                        + participant.getAddress().getZip());
                addressManager.update(participant.getAddress());
                participantManager.update(participant);

                logger.debug("Finished updating participant");
            }
            this.participant = new Participant();
        }
        return "redirect:/participant";

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
    List getSiblings(@RequestParam("term") String query) {
        logger.debug("Entered :" + query);

        return participantManager.searchForSiblings(participant, query);
    }

    public void setParticipantManager(ParticipantManager participantManager) {
        this.participantManager = participantManager;
    }

    public void setAddressManager(AddressManager addressManager) {
        this.addressManager = addressManager;
    }

}
