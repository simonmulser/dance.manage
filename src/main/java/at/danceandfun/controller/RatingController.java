package at.danceandfun.controller;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.danceandfun.entity.Rating;
import at.danceandfun.service.InvoiceManager;
import at.danceandfun.service.ParticipantManager;
import at.danceandfun.service.RatingManager;
import at.danceandfun.util.AppContext;

@Controller
@RequestMapping(value = "admin/rating")
public class RatingController {

    private static Logger logger = Logger.getLogger(RatingController.class);

    @Autowired
    private InvoiceManager invoiceManager;
    @Autowired
    private ParticipantManager participantManager;
    @Autowired
    private RatingManager ratingManager;

    private boolean editTrue = false;

    private Rating rating;

    @PostConstruct
    public void init() {
        rating = new Rating();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listRatings(ModelMap map) {

        if (!editTrue) {
            rating = new Rating();
        }

        map.addAttribute("rating", rating);
        map.addAttribute("ratingList", ratingManager.getEnabledList());
        editTrue = false;
        return "admin/ratingView";
    }

    @RequestMapping(value = "/addAnswer/{rid}", method = RequestMethod.GET)
    public String addRating(ModelMap map, @PathVariable("rid") int rid) {
        logger.debug("addAnswer");
        editTrue = true;
        rating = ratingManager.get(rid);
        return "redirect:/admin/rating";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addRating(
            @ModelAttribute(value = "rating") @Valid Rating rating) {
        this.rating = ratingManager.get(rating.getRid());
        this.rating.setAnswer(rating.getAnswer());
        ratingManager.merge(this.rating);
        this.rating = new Rating();
        editTrue = false;
        return "redirect:/admin/rating";
    }

    public String geti18nMessage(String identifier) {
        Locale locale = LocaleContextHolder.getLocale();
        return AppContext.getApplicationContext().getMessage(identifier, null,
                locale);
    }
}
