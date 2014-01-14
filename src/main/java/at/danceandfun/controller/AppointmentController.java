package at.danceandfun.controller;

import java.util.List;

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

import at.danceandfun.entity.Appointment;
import at.danceandfun.entity.Course;
import at.danceandfun.service.AppointmentManager;
import at.danceandfun.service.CourseManager;
import at.danceandfun.util.AppointmentsWrapper;
import at.danceandfun.util.Helpers;

@Controller
@RequestMapping(value = "admin/appointment")
public class AppointmentController {

    private static Logger logger = Logger
            .getLogger(AppointmentController.class);

    @Autowired
    private CourseManager courseManager;
    @Autowired
    private AppointmentManager appointmentManager;

    @RequestMapping(value = "edit/{slug}", method = RequestMethod.GET)
    public String editAppointments(ModelMap map, @PathVariable String slug) {
        logger.info("edit appointments for course:" + slug);

        List<Appointment> appointments = appointmentManager
                .getEnabledAppointmentsForCourseId(Helpers.extractId(slug));

        AppointmentsWrapper appointmentsWrapper = new AppointmentsWrapper();
        appointmentsWrapper.setAppointments(appointments);

        // map.put("course", course);
        map.put("wrapper", appointmentsWrapper);
        return "admin/appointmentView";
    }

    @RequestMapping(value = "edit/{slug}", method = RequestMethod.POST)
    public String saveAppointments(ModelMap map, @PathVariable String slug,
            @ModelAttribute(value = "wrapper") AppointmentsWrapper wrapper,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.info("appointment form has errors");
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.wrapper",
                    result);
            redirectAttributes.addFlashAttribute("wrapper", wrapper);
            return "admin/appointmentView";
        }

        logger.info("saving appointments for course:" + slug);
        Course course = courseManager.get(Helpers.extractId(slug));

        for (Appointment appointment : wrapper.getAppointments()) {
            appointment.setCourse(course);

            logger.info("updating appointment:" + appointment);
            appointmentManager.merge(appointment);
        }

        for (Appointment appointment : course.getAppointments()) {
            if (!wrapper.getAppointments().contains(appointment)) {
                logger.info("deleting appointment:" + appointment);
                appointment.setEnabled(false);
            }
        }

        map.put("course", course);
        return "redirect:/admin/course";
    }
}
