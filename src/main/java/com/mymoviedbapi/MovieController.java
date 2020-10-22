package com.mymoviedbapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping

public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    //Metoda GET do wyciągnięcia wszystkich filmów
    @GetMapping("/movies/")
    public List<Movie> getAllMovies(){
        return (List<Movie>) movieRepository.findAll();
    }

    //Metoda GET do wyciągnięcia konkretnego filmu
    @GetMapping("/movies/{id}/")
    public ResponseEntity<Movie> getMovieById(@PathVariable(value = "id") Long movieId)
        throws Exception{
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IdNotFoundException("Movie with ID " + movieId + " not found"));
        return ResponseEntity.ok().body(movie);
    }

    //Metoda POST do tworzenia filmu
    @PostMapping("/movies/")
    public Movie createMovie(@Valid @RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    //Metoda PUT do edycji filmu
    @PutMapping("/movies/{id}/")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable(value="id") Long movieId, @Valid @RequestBody Movie movieDetails
    ) throws Exception{
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IdNotFoundException("Movie with ID " + movieId + " not found"));
        movie.setMovieName(movieDetails.getMovieName());
        movie.setMovieDirector(movieDetails.getMovieDirector());
        movie.setMovieGenre(movieDetails.getMovieGenre());
        movie.setMovieImage(movieDetails.getMovieImage());
        movie.setMovieReleaseDate(movieDetails.getMovieReleaseDate().toString());
        movie.setMovieDescription(movieDetails.getMovieDescription());

        final Movie updatedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    //Metoda DELETE do usuwania filmu
    @DeleteMapping("/movies/{id}/")
    public Map<String, Boolean> deleteMovie(@PathVariable(value="id") Long movieId) throws Exception{
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new Exception("Movie with ID " + movieId + " not found"));
        movieRepository.delete(movie);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String onMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String error = "";
        for(FieldError fieldError : e.getBindingResult().getFieldErrors()){
            error += fieldError.getField() + ": " + fieldError.getDefaultMessage() +"\n";
        }
        return error;
    }

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String onIdNotFoundException(IdNotFoundException e){
        return e.getMessage();
    }

    public class IdNotFoundException extends Exception {
        public IdNotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }
}
