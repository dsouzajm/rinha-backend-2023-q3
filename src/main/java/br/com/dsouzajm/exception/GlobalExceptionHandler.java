package br.com.dsouzajm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Retorna o status 422 Unprocessable Entity
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Handler para erros de tipo de dado (ex: "nome" como int)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        // Retorna o status 400 Bad Request
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}