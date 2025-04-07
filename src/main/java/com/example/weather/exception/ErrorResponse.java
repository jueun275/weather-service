package com.example.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String detail;

    public ErrorResponse(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    public static ErrorResponse of(String message, String detail) {
        return new ErrorResponse(message, detail);
    }


}
