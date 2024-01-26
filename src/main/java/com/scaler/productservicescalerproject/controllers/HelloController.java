package com.scaler.productservicescalerproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// With @RestController this will be serving the http Rest APIs. Each Annotation will be creating a bean in Java.
// This class will be serving requests at /hello at the port 8080 on the localhost i.e. http://localhost:8080/hello
@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/say/{name}/{repeatCount}")
    public String sayHello(@PathVariable("name") String name, @PathVariable("repeatCount") int times){
        String answer = "";
        for(int i=1;i<= times;i++){
            answer += "API says Hello " + name + "</br>";
        }
        return answer;
    }
}
