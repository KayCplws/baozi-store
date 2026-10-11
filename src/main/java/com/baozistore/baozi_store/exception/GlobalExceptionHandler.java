package com.baozistore.baozi_store.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarDadosInvalidos(
            MethodArgumentNotValidException ex) {

        List<String> detalhes = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .toList();

        ErroResposta resposta = new ErroResposta(
                "Dados inválidos",
                detalhes
        );

        return ResponseEntity.badRequest().body(resposta);
    }

    public record ErroResposta(
            String erro,
            List<String> detalhes
    ) {}
}
