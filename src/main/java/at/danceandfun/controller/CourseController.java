package at.danceandfun.controller;

/*
 @Controller
 @RequestMapping(value = "/course")
 public class CourseController {

 private static Logger logger = Logger.getLogger(CourseController.class);

 @Autowired
 // TODO MANAGER private CourseManager courseManager;
 private Course course = new Course();

 @RequestMapping(value = "", method = RequestMethod.GET)
 public String listCourses(ModelMap map) {
 logger.debug("LIST Participant with id " + course.getCid());
 map.addAttribute("course", course);
 // TODO Insert manager map.addAttribute("courseList",
 // courseManager.getEnabledList()));

 return "courseView";
 }

 @RequestMapping(value = "/add", method = RequestMethod.POST)
 public String addCourse(@ModelAttribute(value = "course") Course course,
 BindingResult result) {
 logger.debug("ADD Course with id " + course.getCid());
 course.setEnabled(true);
 // TODO MANAGER courseManager.save(course);
 course = new Course();
 return "redirect:/course";
 }

 @RequestMapping(value = "/edit/{cid}")
 public String editCourse(@PathVariable("cid") Integer cid) {
 logger.debug("Edit Course with id " + cid);
 // TODO MANAGER course = courseManager.get(cid);
 return "redirect:/course";
 }

 @RequestMapping(value = "/delete/{cid}")
 public String deleteCourse(@PathVariable("cid") Integer cid) {
 logger.debug("Delete Course with id " + cid);
 // TODO MANAGER course = courseManager.get(cid);
 course.setEnabled(false);
 // TODO MANAGER courseManager.update(course);
 course = new Course();
 return "redirect:/course";
 }

 /*
 * TODO MANAGER public void setCourseManager(CourseManager courseManager) {
 * this.courseManager = courseManager; }
 */

// }
