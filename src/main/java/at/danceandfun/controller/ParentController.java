package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.HashMap;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.service.AddressManager;
import at.danceandfun.service.ParentManager;
import at.danceandfun.service.ParticipantManager;
import at.danceandfun.service.PersonManager;

@Controller
@RequestMapping(value = "admin/parent")
@SessionAttributes("parent")
public class ParentController {

    private static Logger logger = Logger.getLogger(ParentController.class);

    private boolean editTrue = false;

    @Autowired
    private ParentManager parentManager;
    @Autowired
    private PersonManager personManager;
    @Autowired
    private ParticipantManager participantManager;
    @Autowired
    private AddressManager addressManager;

    private Parent parent;

    @PostConstruct
    public void init() {
        parent = new Parent();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listParents(ModelMap map) {
        logger.debug("LIST Parent with id " + parent.getPid());

        if (!editTrue) {
            parent = new Parent();
        }
        map.addAttribute("parent", parent);

        DetachedCriteria criteria = DetachedCriteria.forClass(Parent.class);
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        map.addAttribute("parentList",
                parentManager.getEnabledListWithCriteria(criteria));
        editTrue = false;

        return "admin/parentView";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addParent(@ModelAttribute("parent") @Valid Parent parent,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.parent",
                    result);
            redirectAttributes.addFlashAttribute("parent", parent);
            this.parent = parent;
            editTrue = true;
            return "redirect:/admin/parent#add";

        } else {
            logger.debug("ADD Participant with id " + parent.getPid());
            parent.setEnabled(true);

            if (parent.getAddress().getAid() == null) {
                addressManager.merge(parent.getAddress());
            }

            if (parent.getPid() == null) {
                logger.debug("New parent");
                parent = (Parent) personManager.getURLToken(parent);
                parentManager.persist(parent);
                if (!parent.getEmail().equals("")) {
                    personManager.sendURL(parent);
                }
            } else {
                logger.debug("Update parent");
                parent = parentManager.merge(parent);
                if (!parent.isActivated() && !parent.getEmail().equals("")) {
                    personManager.sendURL(parent);
                }
            }

            logger.debug("Finished updating parent");
            editTrue = false;
        }
        return "redirect:/admin/parent";

    }

    @RequestMapping(value = "/edit/{pid}")
    public String editParent(@PathVariable("pid") Integer pid) {
        logger.debug("Edit Parent with id " + pid);
        parent = parentManager.get(pid);
        editTrue = true;

        return "redirect:/admin/parent#add";
    }

    @RequestMapping(value = "/delete/{pid}")
    public String deleteParent(@PathVariable("pid") Integer pid) {
        logger.debug("Delete Parent with id " + pid);
        parent = parentManager.get(pid);
        parent.setEnabled(false);
        if (parent.getChildren().size() > 0) {
            for (Participant p : parent.getChildren()) {
                p.setParent(null);
                participantManager.merge(p);
            }
            parent.setChildren(new ArrayList<Participant>());
        }

        parentManager.merge(parent);
        parent = new Parent();
        return "redirect:/admin/parent";
    }

    @RequestMapping(value = "/viewParentListPdf", method = RequestMethod.GET)
    public ModelAndView viewParentListPdf() {
        logger.debug("Creating participants by course count pdf");

        HashMap<String, Object> map = new HashMap<String, Object>(1);
        DetachedCriteria criteria = DetachedCriteria.forClass(Parent.class);
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        map.put("parentList",
                parentManager.getEnabledListWithCriteria(criteria));
        return new ModelAndView("viewParentListPdf", map);
    }

    public void setParentManager(ParentManager parentManager) {
        this.parentManager = parentManager;
    }

    public void setAddressManager(AddressManager addressManager) {
        this.addressManager = addressManager;
    }

}
