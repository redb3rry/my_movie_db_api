package com.mymoviedbapi;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "movies")
@EntityListeners(AuditingEntityListener.class)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @Column(name = "movie_director", nullable = false)
    private String movieDirector;

    @Column(name = "movie_genre", nullable = false)
    private String movieGenre;

    @Column(name = "movie_release", nullable = false)
    private Date movieReleaseDate;

    @Lob
    @Column(name = "movie_image", nullable = true)
    private String movieImage;

    @Lob
    @Column(name = "movie_description", nullable = true)
    private String movieDescription;

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public Movie(String movieName, String movieDirector, String movieGenre, String movieReleaseDate, String movieImage, String movieDescription) throws ParseException {
        this.movieName = movieName;
        this.movieDirector = movieDirector;
        this.movieGenre = movieGenre;
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.movieReleaseDate = formater.parse(movieReleaseDate);
        this.movieImage = movieImage;
        this.movieDescription = movieDescription;
    }

    public Movie() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public void setMovieReleaseDate(String releaseYear) throws ParseException {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.movieReleaseDate = formater.parse(releaseYear);
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public long getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public String getMovieReleaseDate() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return formater.format(movieReleaseDate);
    }

    public String getMovieDescription() {
        return movieDescription;
    }
}

interface MovieRepository extends CrudRepository<Movie, Long> {
}
