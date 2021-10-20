package api.card.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import api.card.model.Card;
import api.card.model.CardDto;

@Service
public class CardService {
	/**
	 * Create a card.
	 * 
	 * @param cardDto card data
	 * @return the new card created
	 * @throws Exception
	 */
	public CardData save(CardDto cardDto) throws Exception {
		return null;
	}

	/**
	 * Modify a card.
	 * 
	 * @param cardDto card data
	 * @return the modified card data
	 * @throws Exception
	 */
	public CardData update(CardDto cardDto) throws Exception {
		return null;
	}

	/**
	 * Finds card data by id.
	 * 
	 * @param id id card
	 * @return an optional with the card data if found otherwise an empty optional
	 * @throws Exception
	 */
	public Optional<Card> get(Long id) throws Exception {
		return null;
	}

	/**
	 * Delete a card.
	 * 
	 * @param id id card
	 * @throws Exception
	 */
	public void remove(Long id) throws Exception {

	}
}
