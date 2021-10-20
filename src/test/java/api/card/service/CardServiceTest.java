package api.card.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import api.card.Application;
import api.card.model.Card;
import api.card.model.CardDto;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class CardServiceTest {

	@Autowired
	private CardService cardService;

	private CardDto cardDto;

	private Long customerId;
	private CardData cardData;
	private Long cardId;
	private Optional<Card> optionalCard;

	private void givenCard1() {
		cardDto = new CardDto();
		cardDto.setCustomerId(customerId);
		cardDto.setAccountHolder("Cary Grant");
		cardDto.setExpiryDateYear(2025);
		cardDto.setExpiryDateMonth(6);
		cardDto.setNumber("0000111122223333");
	}

	private void givenCard2() {
		cardDto = new CardDto();
		cardDto.setCustomerId(customerId);
		cardDto.setAccountHolder("Spencer Tracy");
		cardDto.setExpiryDateYear(2025);
		cardDto.setExpiryDateMonth(6);
		cardDto.setNumber("3333111122223333");
	}

	private void givenCard3() {
		cardDto = new CardDto();
		cardDto.setCustomerId(customerId);
		cardDto.setAccountHolder("Charlie Chaplin");
		cardDto.setExpiryDateYear(2026);
		cardDto.setExpiryDateMonth(6);
		cardDto.setNumber("3333222222223333");
	}

	private void givenCard4() {
		cardDto = new CardDto();
		cardDto.setCustomerId(customerId);
		cardDto.setAccountHolder("Stan Laurel");
		cardDto.setExpiryDateYear(2028);
		cardDto.setExpiryDateMonth(2);
		cardDto.setNumber("3333222222223300");
	}

	private void givenCard2Change() {
		cardDto = new CardDto();
		cardDto.setCustomerId(customerId);
		cardDto.setExpiryDateMonth(8);
	}

	private void givenCustomerId1() {
		this.customerId = 1L;
	}

	private void givenCustomerId2() {
		this.customerId = 2L;
	}

	private void givenCustomerId3() {
		this.customerId = 3L;
	}

	private void givenCustomerId4() {
		this.customerId = 4L;
	}

	private void whenSaveIsCalled() throws Exception {
		cardData = cardService.save(cardDto);
	}

	private void whenUpdateIsCalled() throws Exception {
		cardData = cardService.update(cardDto);
	}

	private void whenGetIsCalled() throws Exception {
		optionalCard = cardService.get(cardId);
	}

	private void whenRemoveIsCalled() throws Exception {
		cardService.remove(cardId);
	}

	private void thenCard1IsCreated() {
		Assertions.assertNotNull(cardData);
		Assertions.assertTrue(cardData.getError().isEmpty());
		Card card = cardData.getCard();
		Assertions.assertNotNull(card);
		Assertions.assertEquals(customerId, card.getCustomerId());
		Assertions.assertEquals("Cary Grant", card.getAccountHolderName());
		Assertions.assertEquals("0000111122223333", card.getNumber());
		Assertions.assertNotNull(card.getExpiryDate());
		Assertions.assertEquals(6, card.getExpiryDate().getMonthValue());
		Assertions.assertEquals(2025, card.getExpiryDate().getYear());
	}

	private void thenCard2IsCreated() {
		Assertions.assertNotNull(cardData);
		Assertions.assertTrue(cardData.getError().isEmpty());
		Card card = cardData.getCard();
		Assertions.assertNotNull(card);
		Assertions.assertEquals(customerId, card.getCustomerId());
		Assertions.assertEquals("Spencer Tracy", card.getAccountHolderName());
		Assertions.assertEquals("3333111122223333", card.getNumber());
		Assertions.assertNotNull(card.getExpiryDate());
		Assertions.assertEquals(6, card.getExpiryDate().getMonthValue());
		Assertions.assertEquals(2025, card.getExpiryDate().getYear());
	}

	private void thenCard2IsModified() {
		Assertions.assertNotNull(cardData);
		Assertions.assertTrue(cardData.getError().isEmpty());
		Card card = cardData.getCard();
		Assertions.assertNotNull(card);
		Assertions.assertEquals(customerId, card.getCustomerId());
		Assertions.assertEquals("Spencer Tracy", card.getAccountHolderName());
		Assertions.assertEquals("3333111122223333", card.getNumber());
		Assertions.assertNotNull(card.getExpiryDate());
		Assertions.assertEquals(8, card.getExpiryDate().getMonthValue());
		Assertions.assertEquals(2025, card.getExpiryDate().getYear());
	}

	private void thenCard3IsCreated() {
		Assertions.assertNotNull(cardData);
		Assertions.assertTrue(cardData.getError().isEmpty());
		Card card = cardData.getCard();
		Assertions.assertNotNull(card);
		Assertions.assertEquals(customerId, card.getCustomerId());
		Assertions.assertEquals("Charlie Chaplin", card.getAccountHolderName());
		Assertions.assertEquals("3333222222223333", card.getNumber());
		Assertions.assertNotNull(card.getExpiryDate());
		Assertions.assertEquals(8, card.getExpiryDate().getMonthValue());
		Assertions.assertEquals(2026, card.getExpiryDate().getYear());
	}

	private void thenCard4IsCreated() {
		Assertions.assertNotNull(cardData);
		Assertions.assertTrue(cardData.getError().isEmpty());
		Card card = cardData.getCard();
		Assertions.assertNotNull(card);
		Assertions.assertEquals(customerId, card.getCustomerId());
		Assertions.assertEquals("Stan Laurel", card.getAccountHolderName());
		Assertions.assertEquals("3333222222223322", card.getNumber());
		Assertions.assertNotNull(card.getExpiryDate());
		Assertions.assertEquals(2, card.getExpiryDate().getMonthValue());
		Assertions.assertEquals(2028, card.getExpiryDate().getYear());
	}

	private void thenCardIsDuplicated() {
		Assertions.assertNotNull(cardData);
		Assertions.assertTrue(cardData.getError().isPresent());
		Assertions.assertEquals(cardData.getError().get(), Errors.DUPLICATED_CARD_NUMBER);
	}

	private void thenCardIsMissing() {
		Assertions.assertNotNull(optionalCard);
		Assertions.assertTrue(optionalCard.isEmpty());
	}

	@Test
	public void createNewCard() throws Exception {
		givenCustomerId1();
		givenCard1();
		whenSaveIsCalled();
		thenCard1IsCreated();
	}

	@Test
	public void updateCard() throws Exception {
		givenCustomerId2();
		givenCard2();
		whenSaveIsCalled();
		thenCard2IsCreated();

		whenGetIsCalled();
		givenCard2Change();
		whenUpdateIsCalled();
		thenCard2IsModified();
	}

	@Test
	public void deleteCard() throws Exception {
		givenCustomerId3();
		givenCard3();
		whenSaveIsCalled();
		thenCard3IsCreated();

		whenRemoveIsCalled();
		whenGetIsCalled();
		thenCardIsMissing();
	}

	@Test
	public void duplicatedCardNumber() throws Exception {
		givenCustomerId4();
		givenCard4();
		whenSaveIsCalled();
		thenCard4IsCreated();

		whenSaveIsCalled();
		thenCardIsDuplicated();
	}
}
