package hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// Диспетчер просматривает классы, аннотированные @Controller и обнаруживает методы, аннотированные аннотациями @RequestMapping внутри них.
@Controller // Маркируем класс как контроллер
@RequestMapping("/hello")
public class UserController {

    @GetMapping
    public ModelAndView hello() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        return modelAndView;
    }
}
