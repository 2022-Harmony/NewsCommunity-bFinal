package com.teamharmony.newscommunity.aop.advice;

import com.teamharmony.newscommunity.exception.Error;
import com.teamharmony.newscommunity.exception.ErrorResponse;
import com.teamharmony.newscommunity.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {
    @ExceptionHandler(value = { InvalidRequestException.class })
    public ResponseEntity<Object> handleApiRequestException(InvalidRequestException ex, HttpServletRequest httpServletRequest) {
        List<Error> errorList = new ArrayList<>();

        String fieldName = "invalid Error";
        String message = ex.getMessage();
        String invalidValue = ex.getInvalidValue();

        Error errorMessage = Error.builder()
                                    .field(fieldName)
                                    .invalidValue(invalidValue)
                                    .build();
        errorList.add(errorMessage);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorList(errorList)
                .message(message)
                .requestUrl(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.BAD_REQUEST.toString())
                .resultCode("FAIL")
                .code(ex.getCode())
                .build();

        log.error("Oops so sick, harmony: {}", message , ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e){
        System.out.println(e.getClass().getName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }
}
