package com.teamharmony.newscommunity.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "에러 상태 코드", example = "C402", required = true)
    String statusCode;
    @ApiModelProperty(value = "요청 URL", example = "api/user/comments/{id}", required = true)
    String requestUrl;
    @ApiModelProperty(value = "결과 코드", example = "FAIL", required = true)
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
