package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Style;
import at.danceandfun.entity.Teacher;
import at.danceandfun.service.AddressManager;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.StyleManager;
import at.danceandfun.service.TeacherManager;

@Controller
@RequestMapping(value = "admin/teacher")
public class EditTeacherController {

    private static Logger logger = Logger
            .getLogger(EditTeacherController.class);

    @Autowired
    private TeacherManager teacherManager;
    @Autowired
    private AddressManager addressManager;
    @Autowired
    private StyleManager styleManager;
    @Autowired
    private CourseManager courseManager;

    private Teacher teacher = new Teacher();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listTeachers(ModelMap map) {
        logger.debug("LIST Teacher with id " + teacher.getPid());
        map.addAttribute("teacher", teacher);
        map.addAttribute("teacherList", teacherManager.getEnabledList());

        return "admin/editTeacherList";
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
            return "redirect:/admin/teacher";
        } else {
            logger.debug("ADD Teacher with id " + teacher.getPid());
            teacher.setEnabled(true);

            if (!teacher.getTempCourses().equals("")) {
                String[] courses = teacher.getTempCourses().split(";");
                for (String s : courses) {
                    Course actualCourse = courseManager.get(Math.abs(Integer
                            .parseInt(s)));

                    if (Integer.parseInt(s) < 0) {
                        // TODO: was passiert mit Course?
                        teacher.getCourses().remove(actualCourse);
                    } else if (!teacher.getCourses().contains(actualCourse)) {
                        logger.debug("Neuen Kurs hinzufügen");

                        teacher.getCourses().add(actualCourse);
                    } else {
                        logger.debug("Bestehender Kurs mit name: "
                                + actualCourse.getName());
                    }
                }
                teacher.setTempCourses("");
            }

            if (!teacher.getTempStyles().equals("")) {
                String[] styles = teacher.getTempStyles().split(";");
                for (String s : styles) {
                    Style actualStyle = styleManager.get(Math.abs(Integer
                            .parseInt(s)));

                    if (Integer.parseInt(s) < 0) {
                        logger.debug("Stil löschen: " + actualStyle.getSid());
                        teacher.getStyles().remove(actualStyle);
                    } else if (!teacher.getStyles().contains(actualStyle)) {
                        logger.debug("Neuen Stil hinzufügen: "
                                + actualStyle.getSid());
                        teacher.getStyles().add(actualStyle);
                    } else {
                        logger.debug("Bestehender Stil mit name: "
                                + actualStyle.getName());
                    }
                }
                teacher.setTempStyles("");
            }

            if (teacher.getAddress().getAid() == null) {
                addressManager.save(teacher.getAddress());
            }

            if (teacher.getPid() == null) {
                logger.debug("New teacher");
                teacherManager.save(teacher);

            } else {
                logger.debug("Update teacher");
                teacherManager.update(teacher);
            }

            if (teacher.getCourses().size() > 0) {
                for (Course c : teacher.getCourses()) {
                    c.setTeacher(teacher);
                    courseManager.update(c);
                }
            }
            this.teacher = new Teacher();
        }
        return "redirect:/admin/teacher";
    }

    @RequestMapping(value = "/edit/{pid}")
    public String editTeacher(@PathVariable("pid") Integer pid) {
        logger.debug("Edit Teacher with id " + pid);
        this.teacher = teacherManager.get(pid);

        if (teacherManager.get(pid).getStyles().size() > 0) {
            String actualStyles = "";
            for (Style s : teacher.getStyles()) {
                actualStyles += s.getSid().toString() + ";";
            }
            teacher.setTempStyles(actualStyles);
        }

        if (teacherManager.get(pid).getCourses().size() > 0) {
            String actualCourses = "";
            for (Course c : teacher.getCourses()) {
                actualCourses += c.getCid().toString() + ";";
            }
            teacher.setTempCourses(actualCourses);
        }

        return "redirect:/admin/teacher";
    }

    @RequestMapping(value = "/delete/{pid}")
    public String deleteTeacher(@PathVariable("pid") Integer pid) {
        logger.debug("Delete Teacher with id " + pid);
        this.teacher = teacherManager.get(pid);
        teacher.setEnabled(false);

        if (teacher.getStyles().size() > 0) {
            for (Style s : teacher.getStyles()) {
                s.getTeachers().remove(teacher);
                styleManager.update(s);
            }
            teacher.setStyles(new ArrayList<Style>());
        }
        if (teacher.getCourses().size() > 0) {
            // TODO: P_ID bleibt in Course derzeit enthalten
            teacher.setCourses(new ArrayList<Course>());
        }

        teacherManager.update(teacher);
        teacher = new Teacher();
        return "redirect:/admin/teacher";
    }

    @RequestMapping(value = "/getStyles", method = RequestMethod.GET)
    public @ResponseBody
    List getStyles(@RequestParam("term") String query) {
        logger.debug("Entered :" + query);

        return styleManager.searchForStyles(teacher, query);
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    public @ResponseBody
    List getCourses(@RequestParam("term") String query) {
        logger.debug("Entered coursname:" + query);

        return courseManager.searchForCourses(teacher, query);
    }

    public void setTeacherManager(TeacherManager teacherManager) {
        this.teacherManager = teacherManager;
    }

}
