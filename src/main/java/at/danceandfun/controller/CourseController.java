package at.danceandfun.controller;

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
import at.danceandfun.service.StyleManager;
import at.danceandfun.service.TeacherManager;

@Controller
@RequestMapping(value = "admin/course")
public class CourseController {

    private static Logger logger = Logger.getLogger(CourseController.class);

    private boolean editTrue = false;

    @Autowired
    private CourseManager courseManager;
    @Autowired
    private StyleManager styleManager;
    @Autowired
    private TeacherManager teacherManager;
    @Autowired
    private AddressManager addressManager;

    private Course course;

    @PostConstruct
    public void init() {
        course = new Course();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listCourses(ModelMap map) {
        logger.debug("LIST course with id " + course.getCid());

        if (!editTrue) {
            course = new Course();
        }

        map.addAttribute("course", course);
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);
        criteria.addOrder(Order.asc("name"));
        map.addAttribute("courseList",
                courseManager.getEnabledListWithCriteria(criteria));
        map.addAttribute("addressList", addressManager.getStudioAddresses());
        editTrue = false;
        return "admin/courseView";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCourse(
            @ModelAttribute(value = "course") @Valid Course course,
            BindingResult result, RedirectAttributes redirectAttributes) {
        logger.debug("ADD Course with id " + course.getCid());
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.course",
                    result);
            redirectAttributes.addFlashAttribute("course", course);
            this.course = course;
            editTrue = true;
            return "redirect:/admin/course";
        }
        course.setEnabled(true);

        if (!(course.getAddress().getAid() == null)) {
            course.setAddress(addressManager.get(course.getAddress().getAid()));
        }

        if (!(course.getTeacher().getPid() == null)) {
            Teacher newTeacher = teacherManager.get(course.getTeacher()
                    .getPid());
            course.setTeacher(newTeacher);
        } else {
            course.setTeacher(null);
        }

        if (!(course.getStyle().getSid() == null)) {
            Style newStyle = styleManager.get(course.getStyle().getSid());
            course.setStyle(newStyle);
        }

        if (course.getCid() == null) {
            courseManager.persist(course);
        } else {
            courseManager.merge(course);
        }
        this.course = new Course();
        return "redirect:/admin/course";
    }

    @RequestMapping(value = "/edit/{cid}")
    public String editCourse(@PathVariable("cid") Integer cid) {
        logger.debug("Edit Course with id " + cid);
        course = courseManager.get(cid);
        editTrue = true;
        return "redirect:/admin/course";
    }

    @RequestMapping(value = "/delete/{cid}")
    public String deleteCourse(@PathVariable("cid") Integer cid) {
        logger.debug("Delete Course with id " + cid);
        course = courseManager.get(cid);
        course.setEnabled(false);
        courseManager.merge(course);
        course = new Course();
        return "redirect:/admin/course";
    }

    @RequestMapping(value = "/getStyles", method = RequestMethod.GET)
    public @ResponseBody
    List<Style> getStyles(@RequestParam("term") String query) {
        return styleManager.searchForStyles(course, query);
    }

    @RequestMapping(value = "/getTeachers", method = RequestMethod.GET)
    public @ResponseBody
    List<Teacher> getTeachers(@RequestParam("term") String query) {
        return teacherManager.searchForTeachers(course, query);
    }

    @RequestMapping(value = "/viewCourseListPdf", method = RequestMethod.GET)
    public ModelAndView viewCourseListPdf() {
        logger.debug("Creating course list pdf");

        HashMap<String, Object> map = new HashMap<String, Object>(1);
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);
        criteria.addOrder(Order.asc("name"));
        map.put("courseList",
                courseManager.getEnabledListWithCriteria(criteria));
        return new ModelAndView("viewCourseListPdf", map);
    }

    public void setCourseManager(CourseManager courseManager) {
        this.courseManager = courseManager;
    }
}
