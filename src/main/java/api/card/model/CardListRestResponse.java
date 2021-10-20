package api.card.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CardListRestResponse {
	private int code;
	private String message;
	private List<CardDto> list;

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

	public List<CardDto> getList() {
		return list;
	}

	public void setList(List<CardDto> list) {
		this.list = list;
	}

}
