package com.project.simple_banking_system.exceptions.handler;

import com.project.simple_banking_system.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.simple_banking_system.model.DTOs.Response.ErrorMessageDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exceção lançada quando uma entidade não podê ser encontrada em um repositório existente.
     * @param e Exceção que deve ser lançada
     * @return ErrorMessageDTO uma mensagem de erro personalizada.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleAccountNotFoundError(EntityNotFoundException e ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDTO(e.getMessage()));

    }
    
    /**
     * Exceção lançada quando uma transação não pode ser realizada.
     * @param e Exceção que deve ser lançada
     * @return ErrorMessageDTO uma mensagem de erro personalizada.
     */
    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ErrorMessageDTO> handleBusinessRulesError(InvalidTransactionException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(e.getMessage()));

    }

    
    /**
     * Exceção lançada quando uma classe desabilitada está tentando ser acessada.
     * @param e Exceção que deve ser lançada
     * @return ErrorMessageDTO uma mensagem de erro personalizada.
     */
    @ExceptionHandler(DisabledAccountException.class)
    public ResponseEntity<ErrorMessageDTO> handleBusinessRulesError(DisabledAccountException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(e.getMessage()));

    }


    /**
     * Exceção lançada quando uma data não pode ser validada corretamente
     * @param e Exceção que deve ser lançada
     * @return ErrorMessageDTO uma mensagem de erro personalizada.
     */
    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ErrorMessageDTO> handleValidationRulesError(InvalidDateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(e.getMessage()));
    }

    /**
     * Exceção lançada quando o valor de um enum não pode ser validado corretamente
     * @param e Exceção que deve ser lançada
     * @return ErrorMessageDTO uma mensagem de erro personalizada.
     */
    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<ErrorMessageDTO> handleValidationRulesError(InvalidEnumValueException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(e.getMessage()));
    }
    
    /**
     * Exceção lançada quando a formatação de um ValueObject é invalida
     * @param e Exceção que deve ser lançada
     * @return ErrorMessageDTO uma mensagem de erro personalizada.
     */
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorMessageDTO> handleValidationRulesError(InvalidFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(e.getMessage()));
    }

    /**
     * Exceção lançada quando um elemento é nulo.
     * @param e Exceção que deve ser lançada
     * @return ErrorMessageDTO uma mensagem de erro personalizada.
     */
    @ExceptionHandler(NullElementException.class)
    public ResponseEntity<ErrorMessageDTO> handleNullableElementError(NullElementException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(e.getMessage()));
    }

      /**
     * Exceção lançada quando um elemento é nulo.
     * @param e Exceção que deve ser lançada
     * @return ErrorMessageDTO uma mensagem de erro personalizada.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessageDTO> handleNullableElementError(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(e.getMessage()));
    }

    
    /**
     * Exceção lançada quando o acesso a uma conta é negado
     * @param e Exceção que deve ser lançada.
     * @return ErrorMessageDTO Uma mensagem de erro personalizada.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessageDTO> handleAccessDeniedError(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessageDTO(e.getMessage()));
    }


    
    /**
     * Exceção lançada quando uma authenticação falha.
     * @param e Exceção que deve ser lançada.
     * @return ErrorMessageDTO Uma mensagem de erro personalizada.
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorMessageDTO> handleAuthenticationFailedError(AuthenticationFailedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessageDTO(e.getMessage()));
    }


    /**
     * Exceção lançada quando a decodificação de um token de acesso passado falha.
     * @param e Exceção que deve ser lançada.
     * @return ErrorMessageDTO Uma mensagem de erro personalizada.
     */
    @ExceptionHandler(TokenDecodificationFailedException.class)
    public ResponseEntity<ErrorMessageDTO> handleTokenManipulationError(TokenDecodificationFailedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body((new ErrorMessageDTO(e.getMessage())));
    }


    /**
     * Exceção lançada quando um erro generico ocorre.
     * @param e Exceção que deve ser lançada 
     * @return ErrorMessageDTO Uma mensagem de erro personalizada
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDTO> handleGeneralError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessageDTO("Erro interno no servido"));

    }

    
}
