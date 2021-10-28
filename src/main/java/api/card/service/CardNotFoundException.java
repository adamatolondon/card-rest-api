package api.card.service;

public class CardNotFoundException extends RuntimeException {

	public CardNotFoundException() {
	}

	public CardNotFoundException(String message) {
		super(message);
	}

}
