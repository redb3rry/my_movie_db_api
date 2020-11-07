package com.mymoviedbapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserCrudMockTest {
    @Autowired
    private MockMvc mockMvc;

    //Wywołanie GET na user/data - poprawne
    @Test
    @Transactional
    public void testGetUserData() throws Exception{
        this.mockMvc.perform(get("/user/data/1/")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn();
    }

    //Wywołanie GET na user/data - niepoprawne
    @Test
    @Transactional
    public void testGetUserDataIncorrect() throws Exception {
        this.mockMvc.perform(get("/user/data/100/")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User with ID 100 not found"))
                .andReturn();
    }

    //Wywołanie POST na register - poprawne
    @Test
    @Transactional
    public void testRegister() throws Exception{
        this.mockMvc.perform(post("/register/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"testName\", \"userSurname\": \"testSurname\", \"userEmail\": \"testmail@gmail.com\", \"userPassword\": \"testPassword1\" }"))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().json("{ \"userName\": \"testName\", \"userSurname\": \"testSurname\", \"userEmail\": \"testmail@gmail.com\", \"userPassword\": \"testPassword1\" }"));
    }

    //Wywołanie POST na register - niepoprawne
    @Test
    @Transactional
    public void testRegisterBadBody() throws Exception{
        this.mockMvc.perform(post("/register/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"bad#!@#Name\", \"userSurname\": \"bad()*{Surname\", \"userEmail\": \"gibberish\", \"userPassword\": \"aaa\" }"))
                .andDo(print()).andExpect(status().isBadRequest());
    }
}
