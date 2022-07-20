package com.teamharmony.newscommunity.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InvalidRequestException extends IllegalArgumentException{
    private String invalidValue;
    private String code;

    // 적절치 못한 인자를 넘겨주었을 때 //
    @Builder
    public InvalidRequestException(String message,  String invalidValue, String code){
        super(message);
        this.invalidValue = invalidValue;
        this.code = code;
    }
}
