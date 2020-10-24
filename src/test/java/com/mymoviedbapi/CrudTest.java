package com.mymoviedbapi;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {CrudTest.Initializer.class})
public class CrudTest {

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=jdbc:postgresql://localhost:5432/mymoviedbapi",
                    "spring.datasource.username=postgres",
                    "spring.datasource.password=admin"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
    @Autowired
    MovieController movieController;

    @Test
    @Transactional
    public void getMovieTest(){

        try {
            System.out.println(Long.parseLong("1"));
            ResponseEntity<Movie> response = movieController.getMovieById(Long.parseLong("1"));
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.assertFalse(true);
        }
    }
}
