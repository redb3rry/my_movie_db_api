package com.mymoviedbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MyMovieDbApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMovieDbApiApplication.class, args);
    }

    @RestController
    class HelloController {
        @GetMapping("/")
        String hello() {
            return "Hello World";
        }
    }

}
