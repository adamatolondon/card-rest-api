package api.card.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RestResponse {
	private String status;
	private String message;
	private CardDto cardDto;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
