package com.example.oidcauth.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public Map<String, Object> handleError(HttpServletRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String message = (String) request.getAttribute("jakarta.servlet.error.message");

        errorDetails.put("status", statusCode != null ? statusCode : 500);
        errorDetails.put("error", message != null ? message : "Unknown error");
        errorDetails.put("path", request.getRequestURI());

        return errorDetails;
    }
}