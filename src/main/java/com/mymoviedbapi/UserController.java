package com.mymoviedbapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping
@CrossOrigin
@Validated
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //Metoda GET do wyciągnięcia danych konkretnego użytkownika
    @GetMapping("/users/data/{token}/")
    public ResponseEntity getMovieById(@PathVariable(value = "token") String userToken) {
        List<User> users = (List<User>) userRepository.findAll();
        User foundUser = null;
        boolean inDB = false;
        for (User user : users) {
            if (user.getUserToken().equals(userToken)) {
                inDB = true;
                foundUser = user;
            }
        }
        if (inDB) {
            Map<String, String> response = new HashMap<>();
            response.put("userName", foundUser.getUserName());
            response.put("userSurname", foundUser.getUserSurname());
            response.put("userEmail", foundUser.getUserEmail());
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Metoda POST do rejestracji użytkownika
    @PostMapping("/register/")
    public ResponseEntity registerUser(@Valid @RequestBody User user) {
        if (user.getUserName().isEmpty() || user.getUserSurname().isEmpty() || user.getUserEmail().isEmpty() || user.getUserPassword().isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "400");
            response.put("message", "Not all fields was given");
            return ResponseEntity.badRequest().body(response);
        }
        List<User> users = (List<User>) userRepository.findAll();
        for (User user1 : users) {
            if (user1.getUserEmail().equals(user.getUserEmail())) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "400");
                response.put("message", "User already in DB");
                return ResponseEntity.badRequest().body(response);
            }
        }
        String password = user.getUserPassword();
        if (password.length() < 8) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "400");
            response.put("message", "Too short password");
            return ResponseEntity.badRequest().body(response);
        } else {
            user.setUserToken(UUID.randomUUID().toString());
            userRepository.save(user);
            Map<String, String> response = new HashMap<>();
            response.put("userName", user.getUserName());
            response.put("userSurname", user.getUserSurname());
            response.put("userEmail", user.getUserEmail());
            return ResponseEntity.ok().header("Token", user.getUserToken()).body(response);
        }
    }

}