package com.muesli.controller;

import com.muesli.util.StrResources;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class CommonExceptionAdvice {

    @ExceptionHandler (Exception.class)
    public String except(Exception ex, Model model){

        model.addAttribute("msg", StrResources.ERROR);
        return StrResources.ALERT_MESSAGE_PAGE;
    }

}
