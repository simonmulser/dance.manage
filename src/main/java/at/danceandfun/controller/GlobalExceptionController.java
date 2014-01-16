package at.danceandfun.controller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import at.danceandfun.exception.BusinessException;

@ControllerAdvice
public class GlobalExceptionController {

    private Logger logger = Logger.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessExceptionException(
            BusinessException exception) {
        logger.error("exception" + exception.getMessage());

        ModelAndView model = new ModelAndView("error/exception");
        model.addObject("exception", exception);
        model.addObject("stackTrace", ExceptionUtils.getStackTrace(exception));
        return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception exception) {
        logger.error("exception" + exception.getMessage());

        ModelAndView model = new ModelAndView("error/exception");
        model.addObject("exception", exception);
        model.addObject("stackTrace", ExceptionUtils.getStackTrace(exception));
        return model;

    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView MissingServletRequestParameter(
            MissingServletRequestParameterException exception) {
        logger.error("exception" + exception.getMessage());

        ModelAndView model = new ModelAndView("error/exception");
        model.addObject("exception", exception);
        model.addObject("stackTrace", ExceptionUtils.getStackTrace(exception));
        return model;

    }
}
