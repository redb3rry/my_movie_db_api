package com.mymoviedbapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        this.mockMvc.perform(get("/users/data/1839a268-ce27-402e-bd14-3d8a3c76ab9e/")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Imie")).andExpect(jsonPath("$.userSurname").value("Nazwisko"))
                .andReturn();
    }

    //Wywołanie GET na user/data - niepoprawne
    @Test
    @Transactional
    public void testGetUserDataIncorrect() throws Exception {
        this.mockMvc.perform(get("/users/data/1839a268-ce27-402e-bd14-3d8a3c76ab921/")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    //Wywołanie POST na register - poprawne
    @Test
    @Transactional
    public void testRegister() throws Exception{
        this.mockMvc.perform(post("/register/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"testName\", \"userSurname\": \"testSurname\", \"userEmail\": \"testmail@gmail.com\", \"userPassword\": \"testPassword1\" }"))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().json("{ \"userName\": \"testName\", \"userSurname\": \"testSurname\", \"userEmail\": \"testmail@gmail.com\"}"));
    }

    //Wywołanie POST na register - niepoprawne
    @Test
    @Transactional
    public void testRegisterBadBody() throws Exception{
        this.mockMvc.perform(post("/register/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"bad#!@#Name\", \"userSurname\": \"bad()*{Surname\", \"userEmail\": \"gibberish\", \"userPassword\": \"aaa\" }"))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    //Wywołanie POST na login - poprawne
    @Test
    @Transactional
    public void testLogin() throws Exception{
        this.mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userEmail\": \"testmail@gmail.com\", \"userPassword\": \"testPassword1\" }"))
                .andDo(print()).andExpect(status().isOk());
    }

    //Wywołanie POST na login - niepoprawne dane
    @Test
    @Transactional
    public void testLoginIncorrect() throws Exception{
        this.mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userEmail\": \"testmail@gmail.com\", \"userPassword\": \"invalid\" }"))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Login failed."));
    }

    //Wywołanie DELETE na login - DO POPRAWY TOKEN
    @Test
    @Transactional
    public void testLogout() throws Exception{
        this.mockMvc.perform(delete("/login/").header("Content-Type","application-json")
                .header("Token", "testToken")).andDo(print()).andExpect(status().isOk());
    }

    //Wywołanie DELETE na login niepoprawne - DO POPRAWY TOKEN
    @Test
    @Transactional
    public void testLogoutIncorrect() throws Exception{
        this.mockMvc.perform(delete("/login/").header("Content-Type","application-json")
                .header("Token", "invalidToken")).andDo(print()).andExpect(status().isBadRequest());
    }
}
