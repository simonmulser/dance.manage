package at.danceandfun.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private Teacher teacher = new Teacher();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listTeachers(ModelMap map) {
        logger.debug("LIST Teacher with id " + teacher.getPid());
        map.addAttribute("teacher", teacher);
        map.addAttribute("teacherList", teacherManager.getEnabledList());

        return "editTeacherList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTeacher(
            @ModelAttribute(value = "teacher") @Valid Teacher teacher,
            BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.teacher",
                    result);
            redirectAttributes.addFlashAttribute("teacher", teacher);
            this.teacher = teacher;
            return "redirect:/teacher";
        } else {
            logger.debug("ADD Teacher with id " + teacher.getPid());
            teacher.setEnabled(true);

            if (teacher.getAddress().getAid() == null) {
                addressManager.save(teacher.getAddress());
            }

            if (teacher.getPid() == null) {
                logger.debug("New teacher");
                teacherManager.save(teacher);
            } else {
                logger.debug("Update teacher");
                teacherManager.update(teacher);

                logger.debug("Finished updating teacher");
            }
            this.teacher = new Teacher();
        }
        return "redirect:/teacher";
    }

    @RequestMapping(value = "/edit/{pid}")
    public String editTeacher(@PathVariable("pid") Integer pid) {
        logger.debug("Edit Teacher with id " + pid);
        this.teacher = teacherManager.get(pid);
        return "redirect:/teacher";
    }

    @RequestMapping(value = "/delete/{pid}")
    public String deleteTeacher(@PathVariable("pid") Integer pid) {
        logger.debug("Delete Teacher with id " + pid);
        this.teacher = teacherManager.get(pid);
        teacher.setEnabled(false);
        teacherManager.update(teacher);
        teacher = new Teacher();
        return "redirect:/teacher";
    }

    public void setTeacherManager(TeacherManager teacherManager) {
        this.teacherManager = teacherManager;
    }

}
