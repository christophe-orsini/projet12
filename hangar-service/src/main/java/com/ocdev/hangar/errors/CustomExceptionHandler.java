package com.ocdev.hangar.errors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ErrorHandlingControllerAdvice
{
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e)
	{
		ValidationErrorResponse error = new ValidationErrorResponse();
		for (ConstraintViolation<?> violation : e.getConstraintViolations())
		{
			error.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
		}
		return error;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) 
	{
		ValidationErrorResponse error = new ValidationErrorResponse();
		for (FieldError fieldError : e.getBindingResult().getFieldErrors())
		{
			error.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
		}
		return error;
    }
	
	@ExceptionHandler(AlreadyExistsException.class)
	@ResponseBody
	ResponseEntity<ErrorMessage> onAlreadyExistsException(AlreadyExistsException e)
	{
		ServicesHttpStatus status = ServicesHttpStatus.SERVICES_DUPLICATE;
		ErrorMessage error = new ErrorMessage(status.getCode(), status.getName(), e.getMessage());
		return ResponseEntity.status(status.getCode()).body(error);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	ResponseEntity<ErrorMessage> onEntityNotFoundException(EntityNotFoundException e)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorMessage error = new ErrorMessage(status.value(), status.getReasonPhrase(), e.getMessage());
		return ResponseEntity.status(status.value()).body(error);
	}
	
	@ExceptionHandler(ProxyException.class)
	@ResponseBody
	ResponseEntity<ErrorMessage> onProxyException(ProxyException e)
	{
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		ErrorMessage error = new ErrorMessage(status.value(), status.getReasonPhrase(), e.getMessage());
		return ResponseEntity.status(status.value()).body(error);
	}
}
