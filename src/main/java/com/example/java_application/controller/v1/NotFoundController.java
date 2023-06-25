package com.example.java_application.controller.v1;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.java_application.exceptions.NotFoundException;

@Controller
public class NotFoundController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Implement your custom error handling logic here
        throw new NotFoundException("Route does not exist"); // Return the name of your custom error page (e.g., "404.html")
    }
}
