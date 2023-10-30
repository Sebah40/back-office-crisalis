package com.orange.Crisalis.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ResponseMessage {
    private String message;

    private HttpStatus httpStatus;
}
