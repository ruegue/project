package org.ruegue.project.controller;

import org.ruegue.project.domain.Person;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class SimpleRestController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/send")
//    @ResponseBody
    public Person test(@RequestBody Person p) {
        System.out.println("p = " + p);
        p.setName("ABC");
        p.setAge(p.getAge() + 10);

        return p;
    }

//    @PostMapping("/send")
////    @ResponseBody
//    public Person test2(@RequestBody Person p) {
//        System.out.println("p = " + p);
//        p.setName("ABC");
//        p.setAge(p.getAge() + 10);
//
//        return p;
//    }
}