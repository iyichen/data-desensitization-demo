package org.example.desensitization.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class LogTestController {

    @RequestMapping("/log")
    public String log(HttpServletRequest httpServletRequest, User user) {
        System.out.println("print param: " + getRequestParams(httpServletRequest));
        log.info("param: {}.", getRequestParams(httpServletRequest));
        System.out.println("print user: " + user);
        log.info("user: {}.", user);
        return "ok";
    }

    private Map<String, String> getRequestParams(HttpServletRequest httpServletRequest) {
        Map<String, String[]> paramMap = httpServletRequest.getParameterMap();
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0]);
        }
        return result;
    }

    @Data
    private static class User {

        private String username;

        private String password;
    }
}
