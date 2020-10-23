package com.mymoviedbapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.runner.RunWith;
import org.junit.jupiter.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.mymoviedbapi.Movie;

import java.text.ParseException;
import org.json.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyMovieDbApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyMovieDbApiApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private String getUrl(){
		return "https://mymoviedbapi.herokuapp.com/";
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetAllMovies() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getUrl()+"movies/",
				HttpMethod.GET, entity, String.class);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetMovieById() throws JSONException {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getUrl() + "movies/1/",
				HttpMethod.GET, entity, String.class);
		String jsonString = response.getBody();
		JSONObject object = new JSONObject(jsonString);
		String movieId = object.getString("id");

		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals("1", movieId);
	}

	@Test
	public void testGetMovieByWrongId() throws JSONException {
		int id = 150;
		String expectedMessage = "Movie with ID " + id + " not found";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getUrl() + "movies/" + id + "/",
				HttpMethod.GET, entity, String.class);
		String jsonString = response.getBody();
		JSONObject object = new JSONObject(jsonString);
		String message = object.getString("message");

		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assertions.assertEquals(expectedMessage, message);
	}

	@Test
	public void testDeleteMovieByWrongId() throws JSONException {
		int id = 150;
		String expectedMessage = "Movie with ID " + id + " not found";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getUrl() + "movies/" + id + "/",
				HttpMethod.DELETE, entity, String.class);
		String jsonString = response.getBody();
		JSONObject object = new JSONObject(jsonString);
		String message = object.getString("message");

		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assertions.assertEquals(expectedMessage, message);
	}
}
