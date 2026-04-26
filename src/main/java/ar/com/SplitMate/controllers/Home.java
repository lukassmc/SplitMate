package ar.com.splitmate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {

    @GetMapping("/")
    public String home(){
        System.out.println("ENTRÉ AL CONTROLLER");
        return "home";
    }
}
