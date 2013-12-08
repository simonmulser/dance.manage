package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
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
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.CourseParticipantID;
import at.danceandfun.entity.Participant;
import at.danceandfun.service.AddressManager;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.CourseParticipantManager;
import at.danceandfun.service.ParticipantManager;

@Controller
@RequestMapping(value = "admin/participant")
public class ParticipantController {

    private static Logger logger = Logger
            .getLogger(ParticipantController.class);

    private boolean editTrue = false;

    @Autowired
    private ParticipantManager participantManager;
    @Autowired
    private AddressManager addressManager;
    @Autowired
    private CourseManager courseManager;
    @Autowired
    private CourseParticipantManager courseParticipantManager;

    private Participant participant;

    @PostConstruct
    public void init() {
        participant = new Participant();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listParticipants(ModelMap map) {
        logger.debug("LIST Participant with id " + participant.getPid());

        if (!editTrue) {
            participant = new Participant();
        }
        map.addAttribute("participant", participant);
        map.addAttribute("participantList", participantManager.getEnabledList());
        editTrue = false;

        return "admin/participantView";
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
            editTrue = true;
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
                if (participant.getPid() != null) {
                    participant.setCourseParticipants(participantManager.get(
                            participant.getPid()).getCourseParticipants());
                }
                String[] courses = participant.getTempCourses().split(";");
                for (String s : courses) {
                    Course actualCourse = courseManager.get(Math.abs(Integer
                            .parseInt(s)));

                    if (Integer.parseInt(s) < 0) {
                        if (participant.getCourseById(actualCourse) != null) {
                            CourseParticipant deleteCP = participant
                                    .getCourseById(actualCourse);
                            participant.getCourseParticipants()
                                    .remove(deleteCP);
                            deleteCP.setEnabled(false);
                            courseParticipantManager.update(deleteCP);
                        }
                    } else if (participant.getCourseById(actualCourse) == null) {
                        logger.debug("Neuen Kurs hinzufügen");
                        CourseParticipant newCP = new CourseParticipant();
                        CourseParticipantID newCPID = new CourseParticipantID();
                        newCPID.setCourse(actualCourse);
                        newCPID.setParticipant(participant);
                        newCP.setKey(newCPID);
                        newCP.setEnabled(true);
                        participant.getCourseParticipants().add(newCP);
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
        editTrue = true;

        if (participantManager.get(pid).getSiblings().size() > 0) {
            String actualSiblings = "";
            for (Participant p : participant.getSiblings()) {
                actualSiblings += p.getPid().toString() + ";";
            }
            participant.setTempSiblings(actualSiblings);
        }

        if (participantManager.get(pid).getCourseParticipants().size() > 0) {
            String actualCourses = "";
            for (CourseParticipant cp : participantManager.get(pid)
                    .getCourseParticipants()) {
                if (cp.isEnabled()) {
                    Course actualCourse = cp.getKey().getCourse();
                    actualCourses += actualCourse.getCid().toString() + ";";
                }

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
        if (participant.getSiblings().size() > 0) {
            for (Participant p : participant.getSiblings()) {
                p.getSiblings().remove(participant);
                participantManager.update(p);
            }
            participant.setSiblings(new HashSet<Participant>());
        }
        if (participant.getCourseParticipants().size() > 0) {
            for (CourseParticipant cp : participant.getCourseParticipants()) {
                cp.setEnabled(false);
                courseParticipantManager.update(cp);
            }
            participant
                    .setCourseParticipants(new ArrayList<CourseParticipant>());
        }

        participantManager.update(participant);
        participant = new Participant();
        return "redirect:/admin/participant";
    }

    @RequestMapping(value = "/getSiblings", method = RequestMethod.GET)
    public @ResponseBody
    List<Participant> getSiblings(@RequestParam("term") String query) {
        logger.debug("Entered :" + query);

        return participantManager.searchForSiblings(participant, query);
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    public @ResponseBody
    List<Course> getCourses(@RequestParam("term") String query) {
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
