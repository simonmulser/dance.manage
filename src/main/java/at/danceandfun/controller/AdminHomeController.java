package at.danceandfun.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.Address;
import at.danceandfun.entity.Admin;
import at.danceandfun.entity.Course;
import at.danceandfun.service.AddressManager;
import at.danceandfun.service.AdminManager;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.PersonManager;
import at.danceandfun.service.RatingManager;
import at.danceandfun.util.PasswordBean;

@Controller
@RequestMapping(value = "/admin")
@SessionAttributes("admin")
public class AdminHomeController {

    private Logger logger = Logger.getLogger(AdminHomeController.class);

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private AddressManager addressManager;

    @Autowired
    private RatingManager ratingManager;

    @Autowired
    private AdminManager adminManager;

    @Autowired
    private PersonManager personManager;

    private PasswordBean passwordBean;

    @PostConstruct
    public void init() {
        logger.info("INIT AdminHomeController");
        passwordBean = new PasswordBean();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showIndex(ModelMap map) {
        map.addAttribute("courseList", courseManager.getEnabledList());

        List<Address> studioAddresses = addressManager.getStudioAddresses();
        map.addAttribute("addressList", studioAddresses);

        HashMap<Address, List<Course>> coursePerAddress = new HashMap<Address, List<Course>>();
        Iterator<Address> iter = studioAddresses.iterator();
        while (iter.hasNext()) {
            Address currentAddress = iter.next();
            coursePerAddress.put(currentAddress,
                    courseManager.getCoursesByStudioAddress(currentAddress));
        }
        map.addAttribute("courseByAddressList", coursePerAddress);
        map.addAttribute("newestRatingList", ratingManager.getNewestRatings());

        return "admin/index";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showEdit(ModelMap map) {
        logger.debug("showEdit");
        Admin admin = getLoggedInAdmin();

        map.put("admin", admin);
        return "admin/editAdmin";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateParent(ModelMap map,
            @ModelAttribute("admin") @Valid Admin admin,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.admin",
                    result);
            redirectAttributes.addFlashAttribute("admin", admin);
            return "admin/editAdmin";
        }

        if (!admin.getEmail().equals("")
                && !personManager.getPersonByEmail(admin.getEmail(),
                        admin.getPid()).isEmpty()) {

            logger.error("ConstraintViolation for user with ID"
                    + admin.getPid());
            result.rejectValue("email", "email.constraintViolation");
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.admin",
                    result);
            redirectAttributes.addFlashAttribute("admin", admin);
            return "admin/editAdmin";
        }
        map.put("constraintViolation", 0);
        logger.debug("updateAdmin");
        adminManager.merge(admin);
        return "redirect:/admin";

    }

    @RequestMapping(value = "/editPassword", method = RequestMethod.GET)
    public String showEditPassword(ModelMap map) {
        logger.debug("showEditPassword");

        map.put("password", passwordBean);
        return "admin/editPassword";
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
            return "admin/editPassword";
        }

        logger.info("Try to change password from user with ID: "
                + getLoggedInAdmin().getPid());
        passwordBean.setId(getLoggedInAdmin().getPid());

        if (!personManager.changePassword(passwordBean)) {
            this.passwordBean = passwordBean;
            redirectAttributes.addFlashAttribute("password", passwordBean);
            return "admin/editPassword";
        }

        this.passwordBean = new PasswordBean();
        return "redirect:/admin";

    }


    private Admin getLoggedInAdmin() {
        Admin admin = (Admin) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return adminManager.get(admin.getPid());
    }
}
