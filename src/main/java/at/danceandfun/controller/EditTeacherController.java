package at.danceandfun.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.entity.Teacher;
import at.danceandfun.service.AddressManager;
import at.danceandfun.service.TeacherManager;

@Controller
@RequestMapping(value = "/teacher")
public class EditTeacherController {

    private static Logger logger = Logger
            .getLogger(EditTeacherController.class);

    @Autowired
    private TeacherManager teacherManager;
    @Autowired
    private AddressManager addressManager;

    private Teacher t = new Teacher();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listTeachers(ModelMap map) {
        logger.debug("LIST Teacher with id " + t.getPid());
        map.addAttribute("teacher", this.t);
        map.addAttribute("teacherList", teacherManager.getEnabledList());

        return "editTeacherList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTeacher(
            @ModelAttribute(value = "teacher") Teacher teacher,
            BindingResult result) {
        logger.debug("ADD Teacher with id " + teacher.getPid());
        logger.debug("ADD Teacher with bd " + teacher.getBirthday());
        teacher.setEnabled(true);
        teacherManager.save(teacher);
        if (!teacher.getAddress().equals(null)) {
            addressManager.save(teacher.getAddress());
        }

        this.t = new Teacher();
        return "redirect:/teacher";
    }

    @RequestMapping(value = "/edit/{pid}")
    public String editTeacher(@PathVariable("pid") Integer pid) {
        logger.debug("Edit Teacher with id " + pid);
        this.t = teacherManager.get(pid);
        return "redirect:/teacher";
    }

    @RequestMapping(value = "/delete/{pid}")
    public String deleteTeacher(@PathVariable("pid") Integer pid) {
        logger.debug("Delete Teacher with id " + pid);
        this.t = teacherManager.get(pid);
        t.setEnabled(false);
        teacherManager.update(t);
        // t.getAddress().setEnabled(false);
        // addressManager.update(t.getAddress());
        t = new Teacher();
        return "redirect:/teacher";
    }

    public void setTeacherManager(TeacherManager teacherManager) {
        this.teacherManager = teacherManager;
    }

}
