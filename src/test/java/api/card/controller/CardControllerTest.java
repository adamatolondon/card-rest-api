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
import api.card.model.CardDto;
import api.card.model.ResponseStatus;
import api.card.model.RestResponse;
import api.card.service.Errors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
public class CardControllerTest {
	@LocalServerPort
	private int port;

	private URL url;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private ResponseEntity<RestResponse> response;

	private CardDto cardDto;
	private Long customerId;

	private void givenUrl() throws MalformedURLException {
		url = new URL("http://localhost:" + port + "/card");
	}

	private void givenCardIdUrl() throws MalformedURLException {
		url = new URL("http://localhost:" + port + "/card/" + cardDto.getCardId().toString());
	}

	private void givenCardId100Url() throws MalformedURLException {
		url = new URL("http://localhost:" + port + "/card/100");
	}

	private void givenCustomerId1() {
		this.customerId = 1L;
	}

	private void givenCustomerId2() {
		this.customerId = 2L;
	}

	private void givenCreateCardDto1() {
		cardDto = new CardDto();
		cardDto.setCustomerId(customerId);
		cardDto.setAccountHolder("Cary Grant");
		cardDto.setExpiryDateYear(2025);
		cardDto.setExpiryDateMonth(6);
		cardDto.setNumber("0000111122223333");
	}

	private void givenCreateCardDto2() {
		cardDto = new CardDto();
		cardDto.setCustomerId(customerId);
		cardDto.setAccountHolder("Humphrey Bogart");
		cardDto.setExpiryDateYear(2025);
		cardDto.setExpiryDateMonth(4);
		cardDto.setNumber("7777111122223333");
	}

	private void givenPutCardDto() {
		cardDto.setExpiryDateMonth(7);
//		cardDto.setAccountHolder(null);
//		cardDto.setExpiryDateYear(null);
//		cardDto.setNumber(null);
	}

	private void whenPostIsCalled() throws RestClientException, URISyntaxException {
		response = testRestTemplate.postForEntity(url.toURI(), cardDto, RestResponse.class);
	}

	private void whenGetIsCalled() throws RestClientException, URISyntaxException {
		response = testRestTemplate.getForEntity(url.toURI(), RestResponse.class);
	}

	private void whenDeleteIsCalled() throws RestClientException, URISyntaxException {
		testRestTemplate.delete(url.toURI());
	}

	private void whenPutIsCalled() throws RestClientException, URISyntaxException {
		testRestTemplate.put(url.toURI(), cardDto);
	}

	private void thenOk() {
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
	}

	private void thenCreated() {
		Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
	}

	private void thenBadRequest() {
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
	}

	private void thenNotFound() {
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
	}

	private void thenServerError() {
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode().value());
	}

	private void thenCard1IsCorrect() {
		RestResponse restResponse = response.getBody();
		cardDto = restResponse.getCardDto();
		Assertions.assertNotNull(cardDto);
		Assertions.assertNotNull(cardDto.getCardId());
		Assertions.assertEquals(customerId, cardDto.getCustomerId());
		Assertions.assertEquals("Cary Grant", cardDto.getAccountHolder());
		Assertions.assertEquals("0000111122223333", cardDto.getNumber());
		Assertions.assertEquals(2025, cardDto.getExpiryDateYear());
		Assertions.assertEquals(6, cardDto.getExpiryDateMonth());
	}

	private void thenCard2IsCorrect() {
		RestResponse restResponse = response.getBody();
		cardDto = restResponse.getCardDto();
		Assertions.assertNotNull(cardDto.getCardId());
		Assertions.assertEquals(customerId, cardDto.getCustomerId());
		Assertions.assertEquals("Humphrey Bogart", cardDto.getAccountHolder());
		Assertions.assertEquals("7777111122223333", cardDto.getNumber());
		Assertions.assertEquals(2025, cardDto.getExpiryDateYear());
		Assertions.assertEquals(4, cardDto.getExpiryDateMonth());
	}

	private void thenCard1MonthIsCorrect() {
		RestResponse restResponse = response.getBody();
		cardDto = restResponse.getCardDto();
		Assertions.assertNotNull(cardDto);
		Assertions.assertNotNull(cardDto.getCardId());
		Assertions.assertEquals(customerId, cardDto.getCustomerId());
		Assertions.assertEquals("Cary Grant", cardDto.getAccountHolder());
		Assertions.assertEquals("0000111122223333", cardDto.getNumber());
		Assertions.assertEquals(2025, cardDto.getExpiryDateYear());
		Assertions.assertEquals(7, cardDto.getExpiryDateMonth());
	}

	private void thenCardNumberDuplicated() {
		RestResponse restResponse = response.getBody();
		CardDto cardDto = restResponse.getCardDto();
		Assertions.assertNull(cardDto);
		Assertions.assertNotNull(restResponse.getMessage());
		Assertions.assertTrue(!restResponse.getMessage().isEmpty());
		Assertions.assertEquals(ResponseStatus.ERROR.getStatus(), restResponse.getStatus());
		Assertions.assertEquals(Errors.DUPLICATED_CARD_NUMBER.getMessage(), restResponse.getMessage());
	}

	@Test
	public void newCard() throws MalformedURLException, RestClientException, URISyntaxException {
		givenUrl();
		givenCustomerId1();
		givenCreateCardDto1();
		whenPostIsCalled();
		thenCreated();
		thenCard1IsCorrect();

		// cleanup
		givenCardIdUrl();
		whenDeleteIsCalled();
	}

	@Test
	public void newCardMandatoryData() throws MalformedURLException, RestClientException, URISyntaxException {
		givenUrl();
		givenCreateCardDto1();
		whenPostIsCalled();
		thenBadRequest();
	}

	@Test
	public void getCardById() throws MalformedURLException, RestClientException, URISyntaxException {
		givenUrl();
		givenCustomerId1();
		givenCreateCardDto1();
		whenPostIsCalled();
		thenCreated();
		thenCard1IsCorrect();

		givenCardIdUrl();
		whenGetIsCalled();
		thenOk();
		thenCard1IsCorrect();

		// cleanup
		givenCardIdUrl();
		whenDeleteIsCalled();
	}

	@Test
	public void getCardByIdNotFound() throws MalformedURLException, RestClientException, URISyntaxException {
		givenUrl();
		givenCustomerId1();
		givenCreateCardDto1();

		givenCardId100Url();
		whenGetIsCalled();
		thenNotFound();
	}

	@Test
	public void deleteCard() throws MalformedURLException, RestClientException, URISyntaxException {
		givenUrl();
		givenCustomerId1();
		givenCreateCardDto1();
		whenPostIsCalled();
		thenCreated();
		thenCard1IsCorrect();

		givenCardIdUrl();
		whenDeleteIsCalled();

		whenGetIsCalled();
		thenNotFound();
	}

	@Test
	public void putCard() throws MalformedURLException, RestClientException, URISyntaxException {
		givenUrl();
		givenCustomerId1();
		givenCreateCardDto1();
		whenPostIsCalled();
		thenCreated();
		thenCard1IsCorrect();

		givenCardIdUrl();
		givenPutCardDto();
		whenPutIsCalled();

		whenGetIsCalled();
		thenOk();
		thenCard1MonthIsCorrect();

		// cleanup
		givenCardIdUrl();
		whenDeleteIsCalled();
	}

	@Test
	public void duplicatedCardNumber() throws MalformedURLException, RestClientException, URISyntaxException {
		givenUrl();
		givenCustomerId2();
		givenCreateCardDto2();
		whenPostIsCalled();
		thenCreated();
		thenCard2IsCorrect();

		whenPostIsCalled();
		thenOk();
		thenCardNumberDuplicated();

		// cleanup
		givenCardIdUrl();
		whenDeleteIsCalled();
	}

}
