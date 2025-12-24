package hello.hello_spring.controller;

import org.springframework.ui.Model; //addAttribute()는 Spring MVC Model에만 존재
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello "+ name;  //HTTP의 BODY부에 이 내용을 직접 집어넣는다 (ex)"hello spring"
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello; //처음으로 문자가 아닌 객체를 리턴했당.
        // 단, 스프링컨테이너 측에서 jsonconverter가 디폴트로 작동해서 JSON으로 변환된다.
    }

    static class Hello {
        private String name;

        public String getName() {   //getter setter 단축키는 윈도우는 alt insert -> java bean 규약.?
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}