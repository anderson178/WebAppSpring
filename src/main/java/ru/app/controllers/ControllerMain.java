package ru.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerMain {
    @RequestMapping("/")
    public String getMainPage() {
        return "person.html";
    }

}
