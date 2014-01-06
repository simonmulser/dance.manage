package at.danceandfun.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.Teacher;
import at.danceandfun.service.TeacherManager;

@Controller
@RequestMapping(value = "/teacher")
@SessionAttributes("teacher")
public class TeacherHomeController {

    private static Logger logger = Logger
            .getLogger(TeacherHomeController.class);

    @Autowired
    private TeacherManager teacherManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showIndex(ModelMap map) {
        logger.debug("showIndexTeacher");
        Teacher teacher = getLoggedInTeacher();

        map.put("user", teacher);
        return "teacher/index";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showEdit(ModelMap map) {
        logger.debug("showEditTeacher");
        Teacher teacher = getLoggedInTeacher();

        map.put("teacher", teacher);
        return "teacher/editTeacher";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateParticipant(ModelMap map,
            @ModelAttribute("teacher") @Valid Teacher teacher,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            for (ObjectError oe : result.getAllErrors()) {
                logger.error("ERROR TOSTRING: " + oe.toString());
                logger.error("ERRORS: " + oe.getCode() + " "
                        + oe.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.teacher",
                    result);
            redirectAttributes.addFlashAttribute("teacher", teacher);
            return "redirect:/teacher/edit";
        }

        logger.debug("updateTeacher");
        teacherManager.merge(teacher);
        return "redirect:/teacher";

    }

    private Teacher getLoggedInTeacher() {
        Teacher teacher = (Teacher) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return teacherManager.get(teacher.getPid());
    }
}
