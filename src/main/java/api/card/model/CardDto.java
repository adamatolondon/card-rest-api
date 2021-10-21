package api.card.model;

public class CardDto {
	private Long cardId;
	private Long customerId;
	private String number;
	private String accountHolder;
	private Integer expiryDateYear;
	private Integer expiryDateMonth;

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public Integer getExpiryDateYear() {
		return expiryDateYear;
	}

	public void setExpiryDateYear(Integer expiryDateYear) {
		this.expiryDateYear = expiryDateYear;
	}

	public Integer getExpiryDateMonth() {
		return expiryDateMonth;
	}

	public void setExpiryDateMonth(Integer expiryDateMonth) {
		this.expiryDateMonth = expiryDateMonth;
	}

	public static CardDto of(Card card) {
		CardDto cardDto = new CardDto();
		cardDto.setCardId(card.getId());
		cardDto.setCustomerId(card.getCustomerId());
		cardDto.setAccountHolder(card.getAccountHolderName());
		cardDto.setExpiryDateMonth(card.getExpiryDate().getMonthValue());
		cardDto.setExpiryDateYear(card.getExpiryDate().getYear());
		cardDto.setNumber(card.getNumber());
		return cardDto;
	}
}
