package api.card.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import api.card.model.Card;
import api.card.model.CardDto;
import api.card.model.ResponseStatus;
import api.card.model.RestResponse;
import api.card.service.CardNotFoundException;
import api.card.service.CardService;
import api.card.service.Errors;

@RestController
public class CardController {

	private Logger LOG = LoggerFactory.getLogger(CardController.class);

	@Autowired
	private CardService cardService;

	@PostMapping(path = "/card")
	public ResponseEntity<RestResponse> create(@Valid @RequestBody CardDto cardDto) {
		RestResponse restResponse = new RestResponse();
		try {
			Card card = cardService.save(cardDto);
			restResponse.setCardDto(CardDto.of(card));
			restResponse.setStatus(ResponseStatus.SUCCESS.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			restResponse.setMessage(e.getMessage());
			restResponse.setStatus(ResponseStatus.ERROR.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			restResponse.setMessage(e.getMessage());
			restResponse.setStatus(ResponseStatus.ERROR.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/card/{id}")
	public ResponseEntity<RestResponse> get(@PathVariable("id") Long id) {
		RestResponse restResponse = new RestResponse();
		try {
			Optional<Card> optional = cardService.get(id);
			LOG.info("get: optional=" + optional);
			if (optional.isEmpty()) {
				restResponse.setMessage(Errors.CARD_NOT_FOUND.getMessage());
				restResponse.setStatus(ResponseStatus.ERROR.getStatus());
				return new ResponseEntity<RestResponse>(restResponse, HttpStatus.NOT_FOUND);
			}

			restResponse.setCardDto(CardDto.of(optional.get()));
			restResponse.setStatus(ResponseStatus.SUCCESS.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			restResponse.setMessage(e.getMessage());
			restResponse.setStatus(ResponseStatus.ERROR.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/card/{id}")
	public ResponseEntity<RestResponse> patch(@PathVariable Long id, @Valid @RequestBody CardDto cardDto) {
		RestResponse restResponse = new RestResponse();
		try {
			Card card = cardService.update(id, cardDto);
			restResponse.setCardDto(CardDto.of(card));
			restResponse.setStatus(ResponseStatus.SUCCESS.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
		} catch (CardNotFoundException e) {
			restResponse.setMessage(e.getMessage());
			restResponse.setStatus(ResponseStatus.ERROR.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			restResponse.setMessage(e.getMessage());
			restResponse.setStatus(ResponseStatus.ERROR.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "/card/{id}")
	public ResponseEntity<RestResponse> delete(@PathVariable Long id) {
		RestResponse restResponse = new RestResponse();
		try {
			Card card = cardService.remove(id);
			restResponse.setCardDto(CardDto.of(card));
			restResponse.setStatus(ResponseStatus.SUCCESS.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
		} catch (CardNotFoundException e) {
			restResponse.setMessage(e.getMessage());
			restResponse.setStatus(ResponseStatus.ERROR.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			restResponse.setMessage(e.getMessage());
			restResponse.setStatus(ResponseStatus.ERROR.getStatus());
			return new ResponseEntity<RestResponse>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
