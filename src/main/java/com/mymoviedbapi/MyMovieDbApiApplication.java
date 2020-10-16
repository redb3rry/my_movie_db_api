package com.mymoviedbapi;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class MyMovieDbApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMovieDbApiApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(MovieRepository movieRepository) {
        return args -> {
            movieRepository.save(new Movie("name1", "dir1", "genr1"));
            movieRepository.save(new Movie("name2", "dir2", "genr2"));
        };
    }

}

@RestController
class HelloController {
    private final MovieRepository movieRepository;

    public HelloController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/")
    String hello() {
        return "Hello World";
    }

    @GetMapping("/movies")
    List<Movie> movies() {
        return (List<Movie>) movieRepository.findAll();
    }
}
