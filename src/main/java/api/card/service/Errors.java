package api.card.service;

public enum Errors {
	DUPLICATED_CARD_NUMBER(1, "Duplicated Card Number");

	private int code;
	private String message;

	private Errors(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
