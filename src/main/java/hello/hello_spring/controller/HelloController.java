package hello.hello_spring.controller;

import org.springframework.ui.Model; //addAttribute()는 Spring MVC Model에만 존재
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }
}