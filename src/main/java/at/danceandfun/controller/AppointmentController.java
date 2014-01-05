package at.danceandfun.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

        Course course = courseManager.get(Helpers.extractId(slug));

        AppointmentsWrapper appointmentsWrapper = new AppointmentsWrapper();
        appointmentsWrapper.setAppointments(course.getAppointments());

        map.put("course", course);
        map.put("wrapper", appointmentsWrapper);
        return "admin/appointmentView";
    }

    @RequestMapping(value = "edit/{slug}", method = RequestMethod.POST)
    public String saveAppointments(ModelMap map, @PathVariable String slug,
            @ModelAttribute(value = "wrapper") AppointmentsWrapper wrapper) {
        logger.info("saving appointments for course:" + slug);
        Course course = courseManager.get(Helpers.extractId(slug));

        for (Appointment appointment : wrapper.getAppointments()) {
            appointment.setCourse(course);

            logger.info("updating appointment:" + appointment);
            appointmentManager.merge(appointment);
        }

        map.put("course", course);
        return "admin/appointmentView";
    }
}
