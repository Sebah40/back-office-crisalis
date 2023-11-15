package com.orange.Crisalis.exceptions;

import com.orange.Crisalis.exceptions.custom.*;
import com.orange.Crisalis.exceptions.custom.IllegalArgumentException;
import com.orange.Crisalis.exceptions.custom.NullPointerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            EmptyElementException.class,
            NotCancelableException.class,
            OrderNotFoundException.class,
            IllegalArgumentException.class,
            NullPointerException.class
    })
    @ResponseBody
    public ErrorMessage badRequest(HttpServletRequest request, Exception exception){
        return new ErrorMessage(exception,request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NotFoundException.class,
    })
    @ResponseBody
    public ErrorMessage notFound(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception,request.getRequestURI());
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
        UnauthorizedException.class
    })
    @ResponseBody
    public void unauthorized(){
        //is empty because http in case 401 not supported body response
    }
}