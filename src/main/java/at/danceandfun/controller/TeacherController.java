package at.danceandfun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Style;
import at.danceandfun.entity.Teacher;
import at.danceandfun.service.AddressManager;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.PersonManager;
import at.danceandfun.service.StyleManager;
import at.danceandfun.service.TeacherManager;
import at.danceandfun.util.Helpers;

@Controller
@RequestMapping(value = "admin/teacher")
public class TeacherController {

    private static Logger logger = Logger.getLogger(TeacherController.class);

    private boolean editTrue = false;

    @Autowired
    private TeacherManager teacherManager;
    @Autowired
    private AddressManager addressManager;
    @Autowired
    private StyleManager styleManager;
    @Autowired
    private CourseManager courseManager;
    @Autowired
    private PersonManager personManager;

    private Teacher teacher;

    @PostConstruct
    public void init() {
        teacher = new Teacher();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listTeachers(ModelMap map) {
        logger.debug("LIST Teacher with id " + teacher.getPid());

        if (!editTrue) {
            teacher = new Teacher();
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(Teacher.class);
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        List<Teacher> teacherList = new ArrayList<Teacher>();
        for (Teacher teacher : teacherManager
                .getEnabledListWithCriteria(criteria)) {
            if (teacher.getCourses().size() > 0) {
                teacher.setCourses(courseManager.getEnabledCourses(teacher));
            }
            teacherList.add(teacher);
        }
        map.addAttribute("teacher", teacher);
        map.addAttribute("teacherList", teacherList);
        editTrue = false;
        return "admin/teacherView";
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
            editTrue = true;
            return "redirect:/admin/teacher";
        } else {
            teacher.setEnabled(true);

            if (!teacher.getTempCourses().equals("")) {
                String[] courses = teacher.getTempCourses().split(";");
                for (String s : courses) {
                    Course actualCourse = courseManager.get(Math.abs(Integer
                            .parseInt(s)));
                    teacher.setCourses(courseManager.getEnabledCourses(teacher));
                    if (Integer.parseInt(s) < 0) {
                        teacher.getCourses().remove(actualCourse);
                        actualCourse.setTeacher(null);
                        courseManager.merge(actualCourse);

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
                addressManager.persist(teacher.getAddress());
            }

            if (teacher.getPid() == null) {
                logger.debug("New teacher");
                teacher = (Teacher) personManager.getURLToken(teacher);
                teacher.setPassword(Helpers.PASSWORD_FOR_DUMMY_ACCOUNTS);
                teacherManager.persist(teacher);
                personManager.sendURL(teacher);

            } else {
                logger.debug("Update teacher");
                teacherManager.merge(teacher);
            }

            if (teacher.getCourses().size() > 0) {
                for (Course c : teacher.getCourses()) {
                    c.setTeacher(teacher);
                    courseManager.merge(c);
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
        editTrue = true;

        if (teacher.getStyles().size() > 0) {
            String actualStyles = "";
            String actualStyleNames = "";
            for (Style s : teacher.getStyles()) {
                actualStyles += s.getSid().toString() + ";";
                actualStyleNames += s.getName() + "," + s.getSid().toString()
                        + ";";
            }
            teacher.setTempStyles(actualStyles);
            teacher.setTempStyleNames(actualStyleNames);
        }
        teacher.setCourses(courseManager.getEnabledCourses(teacher));
        if (teacher.getCourses().size() > 0) {
            String actualCourses = "";
            String actualCourseNames = "";
            for (Course c : teacher.getCourses()) {
                actualCourses += c.getCid().toString() + ";";
                actualCourseNames += c.getName() + "," + c.getCid().toString()
                        + ";";
            }
            teacher.setTempCourses(actualCourses);
            teacher.setTempCourseNames(actualCourseNames);
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
                styleManager.merge(s);
            }
            teacher.setStyles(new ArrayList<Style>());
        }
        teacher.setCourses(courseManager.getEnabledCourses(teacher));
        if (teacher.getCourses().size() > 0) {
            for (Course c : teacher.getCourses()) {
                c.setTeacher(null);
                courseManager.merge(c);
            }
            teacher.setCourses(new ArrayList<Course>());
        }

        teacherManager.merge(teacher);
        teacher = new Teacher();
        return "redirect:/admin/teacher";
    }

    @RequestMapping(value = "/getStyles", method = RequestMethod.GET)
    public @ResponseBody
    List<Style> getStyles(@RequestParam("term") String query) {
        return styleManager.searchForStyles(teacher, query);
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    public @ResponseBody
    List<Course> getCourses(@RequestParam("term") String query) {
        return courseManager.searchForCourses(teacher, query);
    }

    @RequestMapping(value = "/viewTeacherListPdf", method = RequestMethod.GET)
    public ModelAndView viewTeacherListPdf() {
        logger.debug("Creating teacher list pdf");

        HashMap<String, Object> map = new HashMap<String, Object>(1);
        DetachedCriteria criteria = DetachedCriteria.forClass(Teacher.class);
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        List<Teacher> teacherList = new ArrayList<Teacher>();
        for (Teacher teacher : teacherManager
                .getEnabledListWithCriteria(criteria)) {
            if (teacher.getCourses().size() > 0) {
                teacher.setCourses(courseManager.getEnabledCourses(teacher));
            }
            teacherList.add(teacher);
        }
        map.put("teacherList", teacherList);
        return new ModelAndView("viewTeacherListPdf", map);
    }

    public void setTeacherManager(TeacherManager teacherManager) {
        this.teacherManager = teacherManager;
    }

}
