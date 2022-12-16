package com.leovegas.walletService.exceptions;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.leovegas.walletService.domainObject.ErrorDO;

/**
 * customException Handler
 * 
 * @author gomathi lakshmanaperumal
 *
 */
@RestController
@ControllerAdvice

public class CustomResponseEntityException {

	@ExceptionHandler(value = { CustomException.class })
	public ResponseEntity<ErrorDO> handleCrudOperationException(CustomException ex, WebRequest webRequest) {
		ErrorDO error = new ErrorDO(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED), ex.getMessage(), new Date(), null);
		return new ResponseEntity<ErrorDO>(error, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDO> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		ErrorDO error = new ErrorDO(String.valueOf(HttpStatus.BAD_REQUEST), null, new Date(), errors);
		return new ResponseEntity<ErrorDO>(error, HttpStatus.BAD_REQUEST);
	}

}
