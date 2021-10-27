package api.card.service;

public class CardNotFoundException extends Exception {

	public CardNotFoundException() {
	}

	public CardNotFoundException(String message) {
		super(message);
	}

}
