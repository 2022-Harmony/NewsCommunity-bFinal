package com.teamharmony.newscommunity.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    String statusCode;
    String requestUrl;
    String code;
    String message;
    String resultCode;

    List<Error> errorList;

    @Builder
    public ErrorResponse(String statusCode, String requestUrl, String code, String message, String resultCode, List<Error> errorList){
        this.statusCode = statusCode;
        this.requestUrl = requestUrl;
        this.code = code;
        this.message = message;
        this.resultCode = resultCode;
        this.errorList = errorList;
    }
}
