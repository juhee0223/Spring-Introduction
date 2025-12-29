package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")        //localhost:8080해서 들어오면, 바로 이게 호출이 된다.
    public String home() {
        return "home";        //home.html이 호출된다.
    }
}
