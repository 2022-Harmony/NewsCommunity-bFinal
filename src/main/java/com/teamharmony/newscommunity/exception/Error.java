package com.teamharmony.newscommunity.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author hyeoking, yj
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Error {
    @ApiModelProperty(value = "에러가 발생한 지점", example = "content", required = true)
    private String field;
    @ApiModelProperty(value = "상태 코드", example = "C402", required = true)
    private String code;
    @ApiModelProperty(value = "메시지", example = "댓글을 불러올 수 없습니다.", required = true)
    private String message;
    @ApiModelProperty(value = "에러를 유발한 지점", example = "뉴스 아이디를 제대로 넣었는지 확인해주세요", required = true)
    private String invalidValue;

    @Builder
    public Error(String field, String code, String message, String invalidValue) {
        this.field = field;
        this.code = code;
        this.message = message;
        this.invalidValue = invalidValue;
    }
}
