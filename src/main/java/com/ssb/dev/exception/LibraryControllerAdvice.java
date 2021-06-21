package com.ssb.dev.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public class LibraryControllerAdvice {
    @ExceptionHandler(value = {DataDiscrepancyException.class, DataNotFoundException.class})
    public ModelAndView DataException(DataDiscrepancyException e) {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
