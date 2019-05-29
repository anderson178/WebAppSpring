package ru.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Денис Мироненко
 * @version $Id$
 * @since 29.05.2019
 */
@Controller
public class ControllerMain {

    /**
     * Метод перехода на страницу person.html
     * @return
     */
    @RequestMapping("/")
    public String getMainPage() {
        return "person.html";
    }

}
