package api.card.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RestResponse {
	private int code;
	private String message;
	private CardDto cardDto;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CardDto getCardDto() {
		return cardDto;
	}

	public void setCardDto(CardDto cardDto) {
		this.cardDto = cardDto;
	}

}
