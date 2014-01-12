package at.danceandfun.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.entity.Address;
import at.danceandfun.entity.Course;
import at.danceandfun.service.AddressManager;
import at.danceandfun.service.CourseManager;

@Controller
@RequestMapping(value = "/admin")
public class AdminHomeController {

    private Logger logger = Logger.getLogger(AdminHomeController.class);

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private AddressManager addressManager;

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

        return "admin/index";
    }

}
