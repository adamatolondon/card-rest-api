package api.card.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

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
import api.card.model.CardListRestResponse;
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
	private ResponseEntity<CardListRestResponse> cardListResponseEntity;
	private RestResponse restResponse;

	private CardDto cardDto;
	private Long customerId;
	private Long cardId;

	private void givenUrl() throws MalformedURLException {
		url = new URL("http://localhost:" + port + "/card");
	}

	private void givenCardIdUrl() throws MalformedURLException {
		url = new URL("http://localhost:" + port + "/card/" + cardId);
	}

	private void givenCustomerId1() {
		this.customerId = 1L;
	}

	private void givenCustomerId2() {
		this.customerId = 2L;
	}

	private void givenCardId1() {
		this.cardId = 1L;
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

	private void givenPatchCardDto() {
		cardDto = new CardDto();
		cardDto.setCardId(cardId);
		cardDto.setExpiryDateMonth(7);
	}

	private void whenPostIsCalled() throws RestClientException, URISyntaxException {
		response = testRestTemplate.postForEntity(url.toURI(), cardDto, RestResponse.class);
	}

	private void whenGetIsCalled() throws RestClientException, URISyntaxException {
		cardListResponseEntity = testRestTemplate.getForEntity(url.toURI(), CardListRestResponse.class);
	}

	private void whenDeleteIsCalled() throws RestClientException, URISyntaxException {
		testRestTemplate.delete(url.toURI());
	}

	private void whenPatchIsCalled() throws RestClientException, URISyntaxException {
		restResponse = testRestTemplate.patchForObject(url.toURI(), cardDto, RestResponse.class);
	}

	private void thenOk() {
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
	}

	private void thenCreated() {
		Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
	}

	private void thenNotFound() {
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
	}

	private void thenServerError() {
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode().value());
	}

	private void thenCard1IsCorrect() {
		RestResponse restResponse = response.getBody();
		CardDto cardDto = restResponse.getCardDto();
		Assertions.assertNotNull(cardDto.getCardId());
		Assertions.assertEquals(customerId, cardDto.getCustomerId());
		Assertions.assertEquals("Cary Grant", cardDto.getAccountHolder());
		Assertions.assertEquals("0000111122223333", cardDto.getNumber());
		Assertions.assertEquals(2025, cardDto.getExpiryDateYear());
		Assertions.assertEquals(6, cardDto.getExpiryDateMonth());
	}

	private void thenCard2IsCorrect() {
		RestResponse restResponse = response.getBody();
		CardDto cardDto = restResponse.getCardDto();
		Assertions.assertNotNull(cardDto.getCardId());
		Assertions.assertEquals(customerId, cardDto.getCustomerId());
		Assertions.assertEquals("Humphrey Bogart", cardDto.getAccountHolder());
		Assertions.assertEquals("7777111122223333", cardDto.getNumber());
		Assertions.assertEquals(2025, cardDto.getExpiryDateYear());
		Assertions.assertEquals(3, cardDto.getExpiryDateMonth());
	}

	private void thenCardListIsCorrect() {
		CardListRestResponse cardListRestResponse = cardListResponseEntity.getBody();
		List<CardDto> list = cardListRestResponse.getList();
		Assertions.assertEquals(1, list.size());
		CardDto cardDto = list.get(0);
		Assertions.assertNotNull(cardDto.getCardId());
		Assertions.assertEquals(customerId, cardDto.getCustomerId());
		Assertions.assertEquals("Cary Grant", cardDto.getAccountHolder());
		Assertions.assertEquals("0000111122223333", cardDto.getNumber());
		Assertions.assertEquals(2025, cardDto.getExpiryDateYear());
		Assertions.assertEquals(6, cardDto.getExpiryDateMonth());
	}

	private void thenCardListIsEmpty() {
		CardListRestResponse cardListRestResponse = cardListResponseEntity.getBody();
		List<CardDto> list = cardListRestResponse.getList();
		Assertions.assertTrue(list.isEmpty());
	}

	private void thenCardMonthIsCorrect() {
		CardDto cardDto = restResponse.getCardDto();
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
		Assertions.assertEquals(Errors.DUPLICATED_CARD_NUMBER.getCode(), restResponse.getCode());
	}

	@Test
	public void newCard() throws MalformedURLException, RestClientException, URISyntaxException {
		givenUrl();
		givenCustomerId1();
		givenCreateCardDto1();
		whenPostIsCalled();
		thenCreated();
		thenCard1IsCorrect();
	}

	@Test
	public void getCardById() throws MalformedURLException, RestClientException, URISyntaxException {
		givenCardIdUrl();
		givenCardId1();
		whenGetIsCalled();
		thenOk();
		thenCard1IsCorrect();
	}

	@Test
	public void deleteCard() throws MalformedURLException, RestClientException, URISyntaxException {
		givenCardIdUrl();
		givenCardId1();
		whenDeleteIsCalled();
		thenOk();

		whenGetIsCalled();
		thenNotFound();
	}

	@Test
	public void patchCard() throws MalformedURLException, RestClientException, URISyntaxException {
		givenUrl();
		givenCardId1();
		givenPatchCardDto();
		whenPatchIsCalled();
		thenOk();

		whenGetIsCalled();
		thenOk();
		thenCardMonthIsCorrect();
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
	}

}
