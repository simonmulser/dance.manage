package at.danceandfun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.service.CourseManager;

@Controller
@RequestMapping(value = "/admin")
public class AdminHomeController {

    @Autowired
    private CourseManager courseManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showIndex(ModelMap map) {
        map.addAttribute("courseList", courseManager.getEnabledList());
        return "admin/index";
    }

}
