package com.artyg.noteapp.rest;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class NoteResponseError {

    private int status;
    @Nullable
    private String message;
    private long timestamp;

    @Contract(pure = true)
    NoteResponseError() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }
}
