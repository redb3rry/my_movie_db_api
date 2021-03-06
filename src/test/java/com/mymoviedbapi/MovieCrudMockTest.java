package com.mymoviedbapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieCrudMockTest {
    @Autowired
    private MockMvc mockMvc;

    //Wywołanie strony domyślnej
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to the MyMovieDB API")));
    }

    //Wywyołanie GET pojedyńczej encji - dobre zapytanie
    @Test
    @Transactional
    public void testGetMovieCorrectId() throws Exception {
        this.mockMvc.perform(get("/movies/1/")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();
    }

    //Wywołanie GET pojedyńczej encji - wybór elementu nie istniejącego
    @Test
    @Transactional
    public void testGetMovieIncorrectId() throws Exception {
        this.mockMvc.perform(get("/movies/100/")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie with ID 100 not found"))
                .andReturn();
    }

    //Wywołanie POST - dobre zapytanie
    @Test
    @Transactional
    public void testPostMovieCorrect() throws Exception {
        this.mockMvc.perform(post("/movies/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"movieName\": \"testPost\", \"movieDirector\": \"testDirector\", \"movieGenre\": \"testGenre\", \"movieReleaseDate\": \"1999-05-16T00:00:00.605Z\", \"movieImage\": \"\", \"movieDescription\": \"\"} "))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"));
    }

    //Wywołanie POST - zapytanie z niepoprawnym body (badRequest)
    @Test
    @Transactional
    public void testPostMovieBadBody() throws Exception {
        this.mockMvc.perform(post("/movies/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"moviName\": \"testPost\", \"moviDirector\": \"testDirector\", \"movieGenre\": \"testGenre\", \"movieReleaseDate\": \"1999-05-16T00:00:00.605Z\", \"movieImage\": \"\", \"movieDescription\": \"\"} "))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    //Wywołanie DELETE - dobre zapytanie
    @Test
    @Transactional
    public void testDeleteMovieCorrect() throws Exception {
        this.mockMvc.perform(delete("/movies/1/")).andDo(print()).andExpect(status().isOk());
    }

    //Wywołanie DELETE - nie istniejący element
    @Test
    @Transactional
    public void testDeleteMovieNotFound() throws Exception {
        this.mockMvc.perform(delete("/movies/111/")).andDo(print()).andExpect(status().isNotFound()).andExpect(jsonPath("$.message").value("Movie with ID 111 not found"));
    }

    @Test
    @Transactional
    public void testPutMovieNotFound() throws Exception {
        this.mockMvc.perform(put("/movies/100/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"movieName\": \"testPost\", \"movieDirector\": \"testDirector\", \"movieGenre\": \"testGenre\", \"movieReleaseDate\": \"1999-05-16T00:00:00.605Z\", \"movieImage\": \"\", \"movieDescription\": \"\"} "))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie with ID 100 not found"))
                .andReturn();
    }

    @Test
    @Transactional
    public void testPutMovieCorrect() throws Exception {
        this.mockMvc.perform(put("/movies/1/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"movieName\": \"testPost\", \"movieDirector\": \"testDirector\", \"movieGenre\": \"testGenre\", \"movieReleaseDate\": \"1999-05-16T00:00:00.605Z\", \"movieImage\": \"\", \"movieDescription\": \"\"} "))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.movieName").value("testPost"))
                .andReturn();
    }

    @Test
    @Transactional
    public void testPutMovieIncorrect() throws Exception {
        this.mockMvc.perform(put("/movies/1/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"mName\": \"testPost\", \"movieDirector\": \"testDirector\", \"movieGenre\": \"testGenre\", \"movieReleaseDate\": \"1999-05-16T00:00:00.605Z\", \"movieImage\": \"\", \"movieDescription\": \"\"} "))
                .andDo(print()).andExpect(status().isBadRequest())
                .andReturn();
    }
}
