package com.mymoviedbapi;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc

public class CrudMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to the MyMovieDB API")));
    }

    @Test
    @Transactional
    public void testGetMovieCorrectId() throws Exception{
            this.mockMvc.perform(get("/movies/1/")).andDo(print()).andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("1"))
                    .andReturn();
    }
    @Test
    @Transactional
    public void testGetMovieIncorrectId() throws Exception{
            this.mockMvc.perform(get("/movies/100/")).andDo(print()).andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Movie with ID 100 not found"))
                    .andReturn();
    }

    @Test
    @Transactional
    public void testPostMovieCorrect() throws Exception {
            this.mockMvc.perform(post("/movies/").contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"movieName\": \"testPost\", \"movieDirector\": \"testDirector\", \"movieGenre\": \"testGenre\", \"movieReleaseDate\": \"1999-05-16T00:00:00.605Z\", \"movieImage\": \"\", \"movieDescription\": \"\"} "))
                    .andDo(print()).andExpect(status().isCreated())
                    .andExpect(content().contentType("application/json"));
    }
}
