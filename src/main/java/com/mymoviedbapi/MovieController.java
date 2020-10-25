package com.mymoviedbapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin
@Validated
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    //Metoda GET do wyciągnięcia wszystkich filmów
    @GetMapping("/movies/")
    public List<Movie> getAllMovies() {
        return (List<Movie>) movieRepository.findAll();
    }

    //Metoda GET do wyciągnięcia konkretnego filmu
    @GetMapping("/movies/{id}/")
    public ResponseEntity<Movie> getMovieById(@PathVariable(value = "id") Long movieId)
            throws Exception {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IdNotFoundException("Movie with ID " + movieId + " not found"));
        return ResponseEntity.ok().body(movie);
    }

    //Metoda POST do tworzenia filmu
    @PostMapping("/movies/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createMovie(@Valid @RequestBody Movie movie) throws JsonProcessingException {
        try {
            movie.checkIfNull();
            Movie movie1 = movieRepository.save(movie);
        } catch (ConstraintViolationException e) {
            Map<String, String> response = onWrongParametersExeption();
            ObjectMapper mapperObj = new ObjectMapper();
            return ResponseEntity.badRequest().body(mapperObj.writeValueAsString(response));
        }
        final Movie movie1 = movieRepository.save(movie);
        return ResponseEntity.created(URI.create("location")).body(movie1);
    }

    //Metoda PUT do edycji filmu
    @PutMapping("/movies/{id}/")
    public ResponseEntity updateMovie(
            @PathVariable(value = "id") Long movieId, @Valid @RequestBody Movie movieDetails
    ) throws Exception {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IdNotFoundException("Movie with ID " + movieId + " not found"));
        try {
            movieDetails.checkIfNull();
            movie.setMovieName(movieDetails.getMovieName());
            movie.setMovieDirector(movieDetails.getMovieDirector());
            movie.setMovieGenre(movieDetails.getMovieGenre());
            movie.setMovieImage(movieDetails.getMovieImage());
            movie.setMovieReleaseDate(movieDetails.getMovieReleaseDate());
            movie.setMovieDescription(movieDetails.getMovieDescription());
        } catch (ParseException e) {
            Map<String, String> response = onWrongParametersExeption();
            ObjectMapper mapperObj = new ObjectMapper();
            return ResponseEntity.badRequest().body(mapperObj.writeValueAsString(response));
        } catch (ConstraintViolationException e) {
            Map<String, String> response = onWrongParametersExeption();
            ObjectMapper mapperObj = new ObjectMapper();
            return ResponseEntity.badRequest().body(mapperObj.writeValueAsString(response));
        }

        final Movie updatedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    //Metoda DELETE do usuwania filmu
    @DeleteMapping("/movies/{id}/")
    public Map<String, Boolean> deleteMovie(@PathVariable(value = "id") Long movieId) throws Exception {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IdNotFoundException("Movie with ID " + movieId + " not found"));
        movieRepository.delete(movie);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String error = "";
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error += fieldError.getField() + ": " + fieldError.getDefaultMessage() + "\n";
        }
        return error;
    }

    @ExceptionHandler(BadParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Map<String, String> onWrongParametersExeption() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Wrong parameter");
        return response;
    }

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    Map<String, String> onIdNotFoundException(IdNotFoundException e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return response;
    }

    public class IdNotFoundException extends Exception {
        public IdNotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }

    public class BadParameterException extends Exception {
        public BadParameterException(String errorMessage) {
            super(errorMessage);
        }
    }
}
