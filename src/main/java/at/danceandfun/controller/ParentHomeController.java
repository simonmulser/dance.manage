package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.service.CourseParticipantManager;
import at.danceandfun.service.ParentManager;
import at.danceandfun.service.ParticipantManager;

@Controller
@RequestMapping(value = "/parent")
public class ParentHomeController {

    private static Logger logger = Logger.getLogger(ParentHomeController.class);

    @Autowired
    private ParentManager parentManager;

    @Autowired
    private ParticipantManager paticipantManager;

    @Autowired
    private CourseParticipantManager courseParticipantManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showIndex(ModelMap map) {
        logger.debug("showIndex");
        Parent parent = getLoggedInParent();

        List<CourseParticipant> enabledCoursesofChildren = new ArrayList<CourseParticipant>();
        for (Participant participant : parent.getChildren()) {
            enabledCoursesofChildren.addAll(courseParticipantManager
                    .getEnabledDistinctCourseParticipants(participant));
        }

        map.put("user", parent);
        map.put("enabledCoursesofChildren", enabledCoursesofChildren);
        return "parent/index";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showEdit(ModelMap map) {
        logger.debug("showEdit");
        Parent parent = getLoggedInParent();

        map.put("parent", parent);
        return "parent/editParent";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateParent(ModelMap map,
            @ModelAttribute("parent") @Valid Parent parent,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.participant",
                    result);
            redirectAttributes.addFlashAttribute("parent", parent);
            return "parent/editParent";
        }

        logger.debug("updateParent");
        parentManager.merge(parent);
        return "redirect:/parent";

    }

    @RequestMapping(value = "/add/child", method = RequestMethod.GET)
    public String showAddChild(ModelMap map) {
        logger.debug("addChild");
        Parent parent = getLoggedInParent();

        map.put("parent", parent);
        return "parent/editChildren";
    }

    @RequestMapping(value = "/add/child", method = RequestMethod.POST)
    public String addChild(ModelMap map,
            @RequestParam("childId") String childId,
            @RequestParam("childPassword") String childPassword,
            BindingResult result, RedirectAttributes redirectAttributes) {

        logger.debug("addChildParent");
        Parent parent = getLoggedInParent();
        Participant particpant = paticipantManager.get(Integer
                .parseInt(childId));

        parentManager.addChild(parent, particpant, childPassword);

        map.put("parent", parent);
        return "parent/editChildren";

    }

    private Parent getLoggedInParent() {
        Parent parent = (Parent) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return parentManager.get(parent.getPid());
    }
}
