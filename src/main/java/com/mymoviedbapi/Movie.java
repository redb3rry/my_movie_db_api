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

    public Movie() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public Movie(String movieName, String movieDirector) {
        this.movieName = movieName;
        this.movieDirector = movieDirector;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
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
}

interface MovieRepository extends CrudRepository<Movie, Long> {
}
