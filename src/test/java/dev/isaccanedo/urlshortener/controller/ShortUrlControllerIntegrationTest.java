package dev.isaccanedo.urlshortener.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import dev.isaccanedo.urlshortener.application.UrlShortenerApplication;
import dev.isaccanedo.urlshortener.controller.ShortUrlController;
import dev.isaccanedo.urlshortener.model.dto.ShortUrlDTO;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

/**
 * Integration tests of Short URL Rest API endpoints
 * 
 * @see ShortUrlController
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = UrlShortenerApplication.class)
@TestMethodOrder(OrderAnnotation.class)
public class ShortUrlControllerIntegrationTest {
	@LocalServerPort
    private int port;
	
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	
	private RestTemplate restTemplate;
	
	@BeforeEach
	public void configureRestAssured() {
		RestAssured.port = this.port;
		RestAssured.defaultParser = Parser.JSON;
	}
	
	@BeforeEach
	public void configureRestTemplate() {
		this.restTemplate = new RestTemplate();
	}
	
	@Test
    @Order(1)
	@Tag("IntegrationTest")
    public void test_loaded_configurations() {
        assertTrue(port > 0);
        assertEquals(this.port, RestAssured.port);
        assertNotNull(restTemplate);
    }
	
	@Test
	@Order(2)
	@Tag("IntegrationTest")
	public void short_long_url_when_not_authenticated_should_return_unauthorized_error() {
		given().
		when().
			post("/").
		then().
			statusCode(HttpStatus.UNAUTHORIZED.value()).and().
			body("error", equalTo("unauthorized")).and().
			body("error_description", equalTo("Full authentication is required to access this resource"));
	}

	@Test
	@Order(3)
	@Tag("IntegrationTest")
	public void short_long_url_passing_no_url_should_return_bad_request_error () {
		given().
			header("Authorization", "Bearer " + getAccessToken()).
		when().
			post("/").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@Order(4)
	@Tag("IntegrationTest")
	public void short_long_url_passing_invalid_long_url_should_return_bad_request_error () {
		String invalidLongUrl = "DGUIHDJFHDF";
		
		given().
			header("Authorization", "Bearer " + getAccessToken()).
			formParam("longUrl", invalidLongUrl).
		when().
			post("/").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
				and().
			body("status", equalTo(HttpStatus.BAD_REQUEST.value())).and().
			body("error", equalTo(HttpStatus.BAD_REQUEST.getReasonPhrase())).and().
			body("message", equalTo("Invalid URL"));
	}
	
	@Test
	@Order(5)
	@Tag("IntegrationTest")
	public void short_long_url_passing_valid_long_url_should_create_new_short_url_and_return_short_url () {
		String longUrl = "https://www.google.com.br/";
		String alias = "zkHVIo";
		
		given().
			header("Authorization", "Bearer " + getAccessToken()).
			formParam("longUrl", longUrl).
		when().
			post("/").
		then().
			statusCode(HttpStatus.OK.value()).
			body("alias", equalTo(alias)).and().
			body("longUrl", equalTo(longUrl));
	}
	
	@Test
	@Order(6)
	@Tag("IntegrationTest")
	public void short_long_url_passing_short_url_as_long_url_should_return_previously_shorted_url () {
		String longUrl = "https://www.google.com/";
		
		ShortUrlDTO shortUrl = given().
				header("Authorization", "Bearer " + getAccessToken()).
				formParam("longUrl", longUrl).
			when()
				.post("/")
			.body().as(ShortUrlDTO.class);
		
		given().
			header("Authorization", "Bearer " + getAccessToken()).
			formParam("longUrl", shortUrl.getShortUrl()).
		when()
			.post("/")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("alias", equalTo(shortUrl.getAlias())).and()
			.body("longUrl", equalTo(shortUrl.getLongUrl())).and()
			.body("shortUrl", equalTo(shortUrl.getShortUrl()));	
	}
	
	@Test
	@Order(7)
	@Tag("IntegrationTest")
	public void redirect_to_original_url_passing_non_existing_short_url_alias_should_return_not_found_error () {
		String invalidShortUrlId = "24u04u2309udidchinvxcklnvcxcvc";
		
		given().
		when().
			get("/" + invalidShortUrlId).
		then().
			statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	@Order(8)
	@Tag("IntegrationTest")
	public void redirect_to_original_url_passing_valid_short_url_alias_and_original_url_found_should_redirect_to_long_url () {
		String longUrl = "https://www.google.com.br";

		ResponseEntity<String> longUrlResponse = restTemplate.getForEntity(longUrl, String.class);
		
		assertTrue(longUrlResponse.getHeaders().containsKey("Set-Cookie"));
		String setCookieContent = "domain=.google.com.br";
		assertTrue(longUrlResponse.getHeaders().get("Set-Cookie").get(0).contains(setCookieContent));
		
		ShortUrlDTO shortUrl = given().
								header("Authorization", "Bearer " + getAccessToken()).
								formParam("longUrl", longUrl).
							when()
								.post("/")
							.body().as(ShortUrlDTO.class);
		
		given().
		when()
			.get("/" + shortUrl.getAlias()).
		then().
			statusCode(HttpStatus.OK.value()).
			 header("Set-Cookie", containsString(setCookieContent));
	}
	
	@Test
	@Order(9)
	@Tag("IntegrationTest")
	public void get_short_url_statistics_when_not_authenticated_should_return_unauthorized_error () {
		String alias = "xxx";
		
		given().
			header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).
		when().
			get("/" + alias + "/statistics").
		then().
			statusCode(HttpStatus.UNAUTHORIZED.value()).
				and().
			body("error", equalTo("unauthorized")).and().
			body("error_description", equalTo("Full authentication is required to access this resource"));
	}
	
	@Test
	@Order(10)
	@Tag("IntegrationTest")
	public void get_short_url_statistics_passing_non_existing_alias_should_return_not_found_error () {
		String alias = "xxx";
		
		given().
			header("Authorization", "Bearer " + getAccessToken()).
			header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).
		when().
			get("/" + alias + "/statistics").
		then().
			statusCode(HttpStatus.NOT_FOUND.value()).
				and().
			body("status", equalTo(HttpStatus.NOT_FOUND.value())).and().
			body("error", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase())).and().
			body("message", equalTo("Short URL not found"));
	}
	
	@Test
	@Order(11)
	@Tag("IntegrationTest")
	public void get_short_url_statistics_passing_existing_alias_should_return_statistics_data () {
		String longUrl = "https://www.example.com";
		
		ShortUrlDTO shortUrl = given().
				header("Authorization", "Bearer " + getAccessToken()).
				formParam("longUrl", longUrl).
			when()
				.post("/")
			.body().as(ShortUrlDTO.class);
		
		given().
		when()
			.get("/" + shortUrl.getAlias());
		
		given().
		when()
			.get("/" + shortUrl.getAlias());
		
		given().
			header("Authorization", "Bearer " + getAccessToken()).
			header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).
		when().
			get("/" + shortUrl.getAlias() + "/statistics").
		then().
			statusCode(HttpStatus.OK.value()).
				and().
			body("totalAccess", equalTo(2)).and().
			body(containsString("lastAccess"));
	}
	
	private String getBasicAuthToken() {
		String toEncode = clientId + ":" + clientSecret;
		return new String(Base64.encodeBase64(toEncode.getBytes()));
	}
	
	private String getAccessToken() {
		String token = "Basic " + getBasicAuthToken();
		
		return given().
				formParam("grant_type", "password").
				formParam("username", "user1").
				formParam("password", "user@2020").
				header("Authorization", token).
			when().
				post("/oauth/token").getBody().jsonPath().getString("access_token");
	}
}
