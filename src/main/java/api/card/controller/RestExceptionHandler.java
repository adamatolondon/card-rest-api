package api.card.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import api.card.model.ResponseStatus;
import api.card.model.RestResponse;
import api.card.service.CardNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
	@SuppressWarnings("unused")
	private Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<RestResponse> illegalArgument(IllegalArgumentException e) {
		RestResponse restResponse = new RestResponse();
		restResponse.setMessage(e.getMessage());
		restResponse.setStatus(ResponseStatus.ERROR.getStatus());
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
	}

	@ExceptionHandler(CardNotFoundException.class)
	public ResponseEntity<RestResponse> cardNotFound(CardNotFoundException e) {
		RestResponse restResponse = new RestResponse();
		restResponse.setMessage(e.getMessage());
		restResponse.setStatus(ResponseStatus.ERROR.getStatus());
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RestResponse> validationError(MethodArgumentNotValidException e) {
		RestResponse restResponse = new RestResponse();
		restResponse.setMessage(e.getMessage());
		restResponse.setStatus(ResponseStatus.ERROR.getStatus());
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RestResponse> internalError(Exception e) {
		RestResponse restResponse = new RestResponse();
		restResponse.setMessage(e.getMessage());
		restResponse.setStatus(ResponseStatus.ERROR.getStatus());
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
