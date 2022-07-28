package com.teamharmony.newscommunity.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
public class Error {
    private String field;
    private String invalidValue;

    @Builder
    public Error(String field, String invalidValue){
        this.field = field;
        this.invalidValue = invalidValue;
    }
}
