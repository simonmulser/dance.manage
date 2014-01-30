package at.danceandfun.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.danceandfun.entity.Admin;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Person;
import at.danceandfun.entity.SuperUser;
import at.danceandfun.entity.Teacher;
import at.danceandfun.service.PersonManager;
import at.danceandfun.util.NameBean;

@Controller
public class SecurityController {

    private static Logger logger = Logger.getLogger(SecurityController.class);

    @Autowired
    private PersonManager personManager;

    private int resetCode = 0; // DEFAULT = 0, NO USER = 1, OK = 2

    private NameBean usernameBean;

    @PostConstruct
    public void init() {
        logger.info("INIT SecurityController");
        usernameBean = new NameBean();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        logger.info("LoginPage loaded");
        HttpSession session = request.getSession();
        session.setAttribute("homeLink", "/login");
        return "login";
    }
    


    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (request.isUserInRole("ROLE_ADMIN")) {
            Admin admin = (Admin) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            session.setAttribute("user", admin);
            session.setAttribute("userType", "admin");
            session.setAttribute("editProfileLink", "/admin/edit");
            session.setAttribute("editPasswordLink", "/admin/editPassword");
            session.setAttribute("homeLink", "/admin");

            return "redirect:/admin";
        }
        if (request.isUserInRole("ROLE_PARTICIPANT")) {
            Participant participant = (Participant) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            Integer pid = participant.getPid();
            session.setAttribute("user", participant);
            session.setAttribute("userType", "participant");
            session.setAttribute("editProfileLink", "/participant/edit/" + pid);
            session.setAttribute("editPasswordLink",
                    "/participant/editPassword/" + pid);
            session.setAttribute("homeLink", "/participant/" + pid);

            return "redirect:/participant/" + pid;
        }
        if (request.isUserInRole("ROLE_PARENT")) {
            Parent parent = (Parent) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            session.setAttribute("user", parent);
            session.setAttribute("userType", "parent");
            session.setAttribute("editProfileLink", "/parent/edit");
            session.setAttribute("editPasswordLink", "/parent/editPassword");
            session.setAttribute("homeLink", "/parent");

            return "redirect:/parent";
        }
        if (request.isUserInRole("ROLE_TEACHER")) {
            Teacher teacher = (Teacher) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            session.setAttribute("user", teacher);
            session.setAttribute("userType", "teacher");
            session.setAttribute("editProfileLink", "/teacher/edit");
            session.setAttribute("editPasswordLink", "/teacher/editPassword");
            session.setAttribute("homeLink", "/teacher");

            return "redirect:/teacher";
        }
        if (request.isUserInRole("ROLE_SUPERUSER")) {
            SuperUser superUser = (SuperUser) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            session.setAttribute("user", superUser);
            session.setAttribute("userType", "superUser");
            session.setAttribute("editProfileLink", "#");
            session.setAttribute("editPasswordLink", "#");
            session.setAttribute("homeLink", "/admin");

            return "redirect:/admin";
        }

        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        logger.error("role does not exists. redirect to login!");
        return "redirect:/login";
    }

    @RequestMapping(value = "error/accessDenied", method = RequestMethod.GET)
    public String accessDenied() {
        logger.info("accessDenied");
        return "error/accessDenied";
    }

    @RequestMapping(value = "error/error404")
    public String get404() {
        return "error/error404";
    }

    @RequestMapping(value = "/validation", method = RequestMethod.GET)
    public String checkAccess(@RequestParam("access") String access,
            ModelMap model) {

        Person person = personManager.getPersonByActivationUUID(access);
        if (person != null) {
            if (!person.isActivated()) {
                person.setActivated(true);
                personManager.update(person);
                model.put("statuscode", 0);
            }else{
                model.put("statuscode", 1);
            }

        } else {
            model.put("statuscode", 2);
        }
        return "validation";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String showEditPassword(ModelMap map) {
        logger.info("showEditPassword");

        map.put("resetCode", resetCode);
        map.put("usernameBean", usernameBean);
        resetCode = 0;
        return "resetPassword";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String changePassword(ModelMap map,
            @ModelAttribute("usernameBean") NameBean usernameBean) {
        logger.info("Try to reset password from user: "
                + usernameBean.getName());
        try {
            Person person = (Person) personManager
                    .loadUserByUsername(usernameBean.getName());
            personManager.getURLToken(person);
            personManager.update(person);

            personManager.sendURL(person);
            resetCode = 2;
            this.usernameBean = new NameBean();
            return "redirect:/resetPassword";

        } catch (UsernameNotFoundException unnfe) {
            logger.error("Username for password RESET not found");
            resetCode = 1;
            this.usernameBean = usernameBean;
            return "redirect:/resetPassword";
        }
    }
}
