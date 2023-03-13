package com.example.custom_exception_handler.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ApiError {

    private HttpStatus status;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;
    private List<String> error;

    public ApiError() {
    }

    public ApiError(HttpStatus status, String message, LocalDateTime timeStamp, List<String> error) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.error = error;
    }

    public ApiError(HttpStatus status, String message, LocalDateTime timeStamp, String error) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.error = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                ", error=" + error +
                '}';
    }
}
