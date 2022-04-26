package org.example.desensitization.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler
    @ResponseBody
    public String handleException(Exception ex) {
        ex.printStackTrace();
        return ex.getMessage();
    }

}
