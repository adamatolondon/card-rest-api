package api.card.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestClientException;

import api.card.Application;
import api.card.model.RestResponse;
import api.card.service.CardService;
import mockit.Expectations;
import mockit.Mocked;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
public class CardControllerExceptionTest {
	@LocalServerPort
	private int port;

	private URL url;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private ResponseEntity<RestResponse> response;

	private Long cardId;
	@Mocked
	private CardService cardService;

	private void givenUrl() throws MalformedURLException {
		url = new URL("http://localhost:" + port + "/card");
	}

	private void givenCardIdUrl() throws MalformedURLException {
		url = new URL("http://localhost:" + port + "/card/" + cardId);
	}

	private void givenCardId1() {
		this.cardId = 1L;
	}

	private void whenGetIsCalled() throws RestClientException, URISyntaxException {
		response = testRestTemplate.getForEntity(url.toURI(), RestResponse.class);
	}

	private void thenServerError() {
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode().value());
	}

	@Test
	public void getCardByIdInternalError() throws Exception {
		new Expectations() {
			{
				cardService.get(anyLong);
				result = new IllegalArgumentException("Argument exception");
			}
		};

		givenUrl();
		givenCardId1();
		givenCardIdUrl();
		whenGetIsCalled();
		thenServerError();
	}

}
