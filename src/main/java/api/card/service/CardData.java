package api.card.service;

import java.util.Optional;

import api.card.model.Card;

public class CardData {
	private Card card;
	private Optional<Errors> error;

	public CardData(Card card, Optional<Errors> error) {
		super();
		this.card = card;
		this.error = error;
	}

	public Card getCard() {
		return card;
	}

	public Optional<Errors> getError() {
		return error;
	}

}
