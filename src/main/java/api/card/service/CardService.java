package api.card.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import api.card.model.Card;
import api.card.model.CardDto;
import api.card.repository.CardRepository;

@Service
public class CardService {
	@Autowired
	private CardRepository cardRepository;

	/**
	 * Create a card.
	 * 
	 * @param cardDto card data
	 * @return the new card created
	 * @throws Exception
	 */
	@Transactional
	public CardData save(CardDto cardDto) throws Exception {
		if (cardDto.getCustomerId() == null || StringUtils.isEmpty(cardDto.getAccountHolder())
				|| cardDto.getExpiryDateMonth() == null || cardDto.getExpiryDateYear() == null
				|| StringUtils.isEmpty(cardDto.getNumber()))
			return new CardData(null, Optional.of(Errors.MANDATORY_DATA));

		List<Card> cards = cardRepository.findByNumberAndCustomerId(cardDto.getNumber(), cardDto.getCustomerId());
		if (!cards.isEmpty())
			return new CardData(null, Optional.of(Errors.DUPLICATED_CARD_NUMBER));

		Card card = new Card();
		card.setCustomerId(cardDto.getCustomerId());
		card.setAccountHolderName(cardDto.getAccountHolder());
		card.setNumber(cardDto.getNumber());
		LocalDate localDate = LocalDate.of(cardDto.getExpiryDateYear(), cardDto.getExpiryDateMonth(), 1);
		card.setExpiryDate(localDate);
		card = cardRepository.save(card);
		return new CardData(card, Optional.empty());
	}

	/**
	 * Modify a card.
	 * 
	 * @param cardDto card data
	 * @return the modified card data
	 * @throws Exception
	 */
	@Transactional
	public CardData update(Long id, CardDto cardDto) throws Exception {
		Optional<Card> optional = cardRepository.findById(id);
		if (optional.isEmpty())
			return new CardData(null, Optional.of(Errors.CARD_NOT_FOUND));

		if (cardDto.getCustomerId() == null || StringUtils.isEmpty(cardDto.getAccountHolder())
				|| cardDto.getExpiryDateMonth() == null || cardDto.getExpiryDateYear() == null
				|| StringUtils.isEmpty(cardDto.getNumber()))
			return new CardData(null, Optional.of(Errors.MANDATORY_DATA));

		Card card = optional.get();
		card.setCustomerId(cardDto.getCustomerId());
		card.setAccountHolderName(cardDto.getAccountHolder());
		card.setNumber(cardDto.getNumber());
		LocalDate localDate = LocalDate.of(cardDto.getExpiryDateYear(), cardDto.getExpiryDateMonth(), 1);
		card.setExpiryDate(localDate);
		card = cardRepository.save(card);
		return new CardData(card, Optional.empty());
	}

	/**
	 * Modify a card.
	 * 
	 * @param cardDto card data
	 * @return the modified card data
	 * @throws Exception
	 */
	@Transactional
	public CardData patch(CardDto cardDto) throws Exception {
		Optional<Card> optional = cardRepository.findById(cardDto.getCardId());
		if (optional.isEmpty())
			return new CardData(null, Optional.of(Errors.CARD_NOT_FOUND));

		Card card = optional.get();
		if (StringUtils.hasLength(cardDto.getAccountHolder()))
			card.setAccountHolderName(cardDto.getAccountHolder());

		if (StringUtils.hasLength(cardDto.getNumber()))
			card.setNumber(cardDto.getNumber());

		if (cardDto.getCustomerId() != null)
			card.setCustomerId(cardDto.getCustomerId());

		if (cardDto.getExpiryDateMonth() != null) {
			LocalDate localDate = card.getExpiryDate();
			localDate = localDate.withMonth(cardDto.getExpiryDateMonth());
			card.setExpiryDate(localDate);
		}

		if (cardDto.getExpiryDateYear() != null) {
			LocalDate localDate = card.getExpiryDate();
			localDate = localDate.withYear(cardDto.getExpiryDateYear());
			card.setExpiryDate(localDate);
		}

		return new CardData(cardRepository.save(card), Optional.empty());
	}

	/**
	 * Finds card data by id.
	 * 
	 * @param id id card
	 * @return an optional with the card data if found otherwise an empty optional
	 * @throws Exception
	 */
	@Transactional
	public Optional<Card> get(Long id) throws Exception {
		return cardRepository.findById(id);
	}

	/**
	 * Delete a card.
	 * 
	 * @param id id card
	 * @throws Exception
	 */
	@Transactional
	public CardData remove(Long id) throws Exception {
		Optional<Card> optional = cardRepository.findById(id);
		if (optional.isEmpty())
			return new CardData(null, Optional.of(Errors.CARD_NOT_FOUND));

		cardRepository.delete(optional.get());
		return new CardData(optional.get(), Optional.empty());
	}
}
