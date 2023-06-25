package com.example.java_application.controller.v1;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.java_application.exceptions.NotFoundException;

@Controller
public class NotFoundController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        throw new NotFoundException("Route does not exist");
    }
}
