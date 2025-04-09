package br.lorenzo.edutech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            AlunoNaoEncontradoException.class,
            CursoNaoEncontradoException.class,
            CategoriaNaoEncontradaException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(RuntimeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler({
            EmailDuplicadoException.class,
            MatriculaDuplicadaException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConflict(RuntimeException ex) {
        return ex.getMessage();
    }
}