package com.artyg.noteapp.rest;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NoteResExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<NoteResponseError> handleNotFoundException(@NotNull NoteNotFoundException exception) {
        return responseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<NoteResponseError> handleException(@NotNull Exception exception) {
        return responseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @Contract("_, _ -> new")
    @NotNull
    private ResponseEntity<NoteResponseError> responseEntity(@NotNull HttpStatus notFound, @Nullable String message) {
        NoteResponseError error = new NoteResponseError();
        error.setStatus(notFound.value());
        error.setMessage(message);
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, notFound);
    }
}
