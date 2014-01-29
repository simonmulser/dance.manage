package at.danceandfun.controller;

import javax.annotation.PostConstruct;
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
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.PersonManager;
import at.danceandfun.service.TeacherManager;
import at.danceandfun.util.PasswordBean;

@Controller
@RequestMapping(value = "/teacher")
@SessionAttributes("teacher")
public class TeacherHomeController {

    private static Logger logger = Logger
            .getLogger(TeacherHomeController.class);

    @Autowired
    private TeacherManager teacherManager;

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private PersonManager personManager;

    private PasswordBean passwordBean;

    @PostConstruct
    public void init() {
        logger.info("INIT ParentHomeController");
        passwordBean = new PasswordBean();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showIndex(ModelMap map) {
        logger.debug("showIndexTeacher");
        Teacher teacher = getLoggedInTeacher();

        map.put("teacher", teacher);
        map.put("user", teacher);
        map.put("enabledCourses", courseManager.getEnabledCourses(teacher));
        return "teacher/index";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showEdit(ModelMap map) {
        logger.debug("showEditTeacher");
        Teacher teacher = getLoggedInTeacher();

        map.put("teacher", teacher);
        map.put("user", teacher);
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
            return "teacher/editTeacher";
        }

        if (!teacher.getEmail().equals("")
                && !personManager.getPersonByEmail(teacher.getEmail())
                        .isEmpty()) {
            logger.error("ConstraintViolation for user with ID"
                    + teacher.getPid());
            result.rejectValue("email", "email.constraintViolation");
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.teacher",
                    result);
            redirectAttributes.addFlashAttribute("teacher", teacher);
            return "teacher/editTeacher";
        }

        logger.debug("updateTeacher");
        teacherManager.merge(teacher);
        return "redirect:/teacher";

    }

    @RequestMapping(value = "/editPassword", method = RequestMethod.GET)
    public String showEditPassword(ModelMap map) {
        logger.debug("showEditPassword");

        map.put("password", passwordBean);
        return "teacher/editPassword";
    }

    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    public String changePassword(ModelMap map,
            @ModelAttribute("password") @Valid PasswordBean passwordBean,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.password",
                    result);
            redirectAttributes.addFlashAttribute("password", passwordBean);
            this.passwordBean = passwordBean;
            return "teacher/editPassword";
        }

        logger.info("Try to change password from user with ID: "
                + getLoggedInTeacher().getPid());
        passwordBean.setId(getLoggedInTeacher().getPid());

        if (!personManager.changePassword(passwordBean)) {
            logger.debug("OBJECT: " + passwordBean);
            this.passwordBean = passwordBean;
            redirectAttributes.addFlashAttribute("password", passwordBean);
            return "teacher/editPassword";
        }

        this.passwordBean = new PasswordBean();
        return "redirect:/teacher";

    }

    @RequestMapping(value = "/absence", method = RequestMethod.GET)
    public String showAbsence(ModelMap map) {
        logger.debug("showAbsenceTeacher");
        Teacher teacher = getLoggedInTeacher();

        map.put("teacher", teacher);
        map.put("user", teacher);
        map.put("enabledCourses", courseManager.getEnabledCourses(teacher));
        return "teacher/absenceView";
    }

    private Teacher getLoggedInTeacher() {
        Teacher teacher = (Teacher) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return teacherManager.get(teacher.getPid());
    }
}
