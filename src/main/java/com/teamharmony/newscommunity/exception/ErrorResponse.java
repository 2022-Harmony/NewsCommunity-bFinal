package com.teamharmony.newscommunity.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;



/**
 *
 * @author hyeoking, yj
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ErrorResponse {
    String statusCode;
    String requestUrl;
    String resultCode;

    List<Error> errorList;

    @Builder
    public ErrorResponse(String statusCode, String requestUrl, String resultCode, List<Error> errorList){
        this.statusCode = statusCode;
        this.requestUrl = requestUrl;
        this.resultCode = resultCode;
        this.errorList = errorList;
    }
}
