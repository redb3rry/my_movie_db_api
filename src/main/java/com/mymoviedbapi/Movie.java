package com.mymoviedbapi;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;

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

    @Column(name = "movie_release_year", nullable = false)
    private int movieReleaseYear;

    @Column(name = "movie_image", nullable = true)
    private String movieImage;

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

    public Movie() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public Movie(String movieName, String movieDirector, String movieGenre, int movieReleaseYear) {
        this.movieName = movieName;
        this.movieDirector = movieDirector;
        this.movieGenre = movieGenre;
        this.movieReleaseYear = movieReleaseYear;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public void setMovieReleaseYear(int releaseYear) { this.movieReleaseYear = releaseYear; }

    public long getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public int getMovieReleaseYear() { return movieReleaseYear; }
}

interface MovieRepository extends CrudRepository<Movie, Long> {
}
