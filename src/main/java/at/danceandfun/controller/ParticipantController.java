package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.service.AddressManager;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.CourseParticipantManager;
import at.danceandfun.service.ParentManager;
import at.danceandfun.service.ParticipantManager;
import at.danceandfun.service.PersonManager;

@Controller
@RequestMapping(value = "admin/participant")
@SessionAttributes("participant")
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
    @Autowired
    private ParentManager parentManager;
    @Autowired
    private PersonManager personManager;

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
        List<Participant> participantList = new ArrayList<Participant>();
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        for (Participant p : participantManager
                .getEnabledListWithCriteria(criteria)) {
            if (p.getCourseParticipants().size() > 0) {
                p.setCourseParticipants(courseParticipantManager
                        .getEnabledDistinctCourseParticipants(p));
            }
            participantList.add(p);
        }

        map.addAttribute("participant", participant);
        map.addAttribute("participantList", participantList);
        editTrue = false;

        return "admin/participantView";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addParticipant(
            @ModelAttribute("participant") @Valid Participant participant,
            BindingResult result, RedirectAttributes redirectAttributes) {

        if (!participant.getEmail().equals("")
                && !personManager.getPersonByEmail(participant.getEmail(),
                        participant.getPid()).isEmpty()) {
            logger.error("ConstraintViolation for user with ID"
                    + participant.getPid());
            result.rejectValue("email", "email.constraintViolation");
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.participant",
                    result);
            redirectAttributes.addFlashAttribute("participant", participant);
            this.participant = participant;
            editTrue = true;
            return "redirect:/admin/participant#add";
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.participant",
                    result);
            redirectAttributes.addFlashAttribute("participant", participant);
            this.participant = participant;
            editTrue = true;
            return "redirect:/admin/participant#add";

        }

        logger.debug("ADD Participant with id " + participant.getPid());
        participant.setEnabled(true);
        if (!(participant.getParent().getPid() == null)) {
            Parent newParent = parentManager.get(participant.getParent()
                    .getPid());
            participant.setParent(newParent);
        } else {
            participant.setParent(null);
        }

        if (participant.getAddress().getAid() == null) {
            addressManager.persist(participant.getAddress());
        }

        if (!(participant.getParent() == null)) {
            Parent newParent = parentManager.get(participant.getParent()
                    .getPid());
            participant.setParent(newParent);
        } else {
            participant.setParent(null);
        }

        if (participant.getPid() == null) {
            logger.debug("New participant");
            participant = (Participant) personManager.getURLToken(participant);
            participantManager.persist(participant);
            if (!participant.getEmail().equals("")) {
                personManager.sendURL(participant);
            }
        }

        if (!participant.getTempSiblings().equals("")) {
            String[] siblings = participant.getTempSiblings().split(";");
            for (String s : siblings) {
                Participant actualParticipant = participantManager.get(Math
                        .abs(Integer.parseInt(s)));

                if (Integer.parseInt(s) < 0) {
                    actualParticipant.getSiblings().remove(participant);
                    participant.getSiblings().remove(actualParticipant);
                    participantManager.merge(participant);
                    participantManager.merge(actualParticipant);
                } else if (!participant.getSiblings().contains(
                        actualParticipant)) {
                    participant.getSiblings().add(actualParticipant);
                }
            }
        }

        if (!participant.getTempCourses().equals("")) {
            if (participant.getPid() != null) {
                participant.setCourseParticipants(courseParticipantManager
                        .getEnabledCourseParticipants(participantManager
                                .get(participant.getPid())));
            }
            String[] courses = participant.getTempCourses().split(";");
            for (String s : courses) {
                Course actualCourse = courseManager.get(Math.abs(Integer
                        .parseInt(s)));

                if (Integer.parseInt(s) < 0) {
                    if (participant.getCourseParticipantsById(actualCourse) != null) {
                        List<CourseParticipant> deleteCPs = participant
                                .getCourseParticipantsById(actualCourse);
                        for (CourseParticipant cp : deleteCPs) {
                            participant.getCourseParticipants().remove(cp);
                            cp.setEnabled(false);
                            courseParticipantManager.merge(cp);
                        }

                    }
                } else if (participant.getCourseParticipantsById(actualCourse) == null) {
                    CourseParticipant newCP = new CourseParticipant();
                    newCP.setCourse(actualCourse);
                    newCP.setParticipant(participant);
                    newCP.setEnabled(true);
                    participant.getCourseParticipants().add(newCP);
                }
            }
        }

        participant = participantManager.merge(participant);
        if (!participant.isActivated() && !participant.getEmail().equals("")) {
            personManager.sendURL(participant);
        }
        this.participant = new Participant();

        return "redirect:/admin/participant";
    }

    @RequestMapping(value = "/edit/{pid}")
    public String editParticipant(@PathVariable("pid") Integer pid) {
        logger.debug("Edit Participant with id " + pid);
        participant = participantManager.get(pid);
        editTrue = true;

        if (participantManager.get(pid).getSiblings().size() > 0) {
            String actualSiblings = "";
            String actualSiblingNames = "";
            for (Participant p : participant.getSiblings()) {
                actualSiblings += p.getPid().toString() + ";";
                actualSiblingNames += p.getFirstname() + " " + p.getLastname()
                        + "," + p.getPid().toString() + ";";
            }
            participant.setTempSiblings(actualSiblings);
            participant.setTempSiblingNames(actualSiblingNames);
        }

        participant.setCourseParticipants(courseParticipantManager
                .getEnabledDistinctCourseParticipants(participant));
        if (participant.getCourseParticipants().size() > 0) {
            String actualCourses = "";
            String actualCourseNames = "";
            for (CourseParticipant cp : participant.getCourseParticipants()) {
                if (cp.isEnabled()) {
                    Course actualCourse = cp.getCourse();
                    actualCourses += actualCourse.getCid().toString() + ";";
                    actualCourseNames += actualCourse.getName() + ","
                            + actualCourse.getCid().toString() + ";";
                }

            }
            participant.setTempCourses(actualCourses);
            participant.setTempCourseNames(actualCourseNames);
        }

        return "redirect:/admin/participant#add";
    }

    @RequestMapping(value = "/delete/{pid}")
    public String deleteParticipant(@PathVariable("pid") Integer pid) {
        logger.debug("Delete Participant with id " + pid);
        participant = participantManager.get(pid);
        participant.setEnabled(false);
        if (participant.getSiblings().size() > 0) {
            for (Participant p : participant.getSiblings()) {
                p.getSiblings().remove(participant);
                participantManager.merge(p);
            }
            participant.setSiblings(new HashSet<Participant>());
        }
        participant.setCourseParticipants(courseParticipantManager
                .getEnabledCourseParticipants(participant));
        if (participant.getCourseParticipants().size() > 0) {
            for (CourseParticipant cp : participant.getCourseParticipants()) {
                cp.setEnabled(false);
                courseParticipantManager.merge(cp);
            }
            participant
                    .setCourseParticipants(new ArrayList<CourseParticipant>());
        }

        participantManager.merge(participant);
        participant = new Participant();
        return "redirect:/admin/participant";
    }

    @RequestMapping(value = "/getParents", method = RequestMethod.GET)
    public @ResponseBody
    List<Parent> getParents(@RequestParam("term") String query) {
        return parentManager.searchForParents(participant, query);
    }

    @RequestMapping(value = "/getSiblings", method = RequestMethod.GET)
    public @ResponseBody
    List<Participant> getSiblings(@RequestParam("term") String query) {
        return participantManager.searchForSiblings(participant, query);
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    public @ResponseBody
    List<Course> getCourses(@RequestParam("term") String query) {
        return courseManager.searchForCourses(participant, query);
    }

    @RequestMapping(value = "/viewParticipantListPdf", method = RequestMethod.GET)
    public ModelAndView viewParticipantListPdf() {
        logger.debug("Creating participants list pdf");

        HashMap<String, Object> map = new HashMap<String, Object>(1);
        List<Participant> participantList = new ArrayList<Participant>();
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        for (Participant p : participantManager
                .getEnabledListWithCriteria(criteria)) {
            if (p.getCourseParticipants().size() > 0) {
                p.setCourseParticipants(courseParticipantManager
                        .getEnabledDistinctCourseParticipants(p));
            }
            participantList.add(p);
        }
        map.put("participantList", participantList);
        return new ModelAndView("viewParticipantListPdf", map);
    }

    public void setParticipantManager(ParticipantManager participantManager) {
        this.participantManager = participantManager;
    }

    public void setAddressManager(AddressManager addressManager) {
        this.addressManager = addressManager;
    }

}
