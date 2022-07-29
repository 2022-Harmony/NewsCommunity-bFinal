package com.teamharmony.newscommunity.aop.advice;

import com.teamharmony.newscommunity.exception.*;
import com.teamharmony.newscommunity.exception.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.teamharmony.newscommunity.domain.auth.util.CookieUtil.removeRefCookie;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {

    /**
     * 컨트롤러 레벨에서 우리 팀이 정의한 요청 값에 의한 에러인 InvalidRequestException이 발생시,
     * 커스텀한 errorMessage를 클라이언트로 내려주기 위한 에러 핸들러
     * @param ex
     * @param httpServletRequest
     * @return 각 도메인에서 정의한 에러 코드와 에러 메시지등이 담긴 Response를 리턴
     */

    @ExceptionHandler(value = { InvalidRequestException.class })
    public ResponseEntity<Object> handleApiRequestException(InvalidRequestException ex, HttpServletRequest httpServletRequest) {
        List<Error> errorList = new ArrayList<>();

        String fieldName = "invalid Error";
        String message = ex.getMessage();
        String invalidValue = ex.getInvalidValue();
        String code = ex.getCode();

        Error errorMessage = Error.builder()
                                  .field(fieldName)
                                  .code(code)
                                  .message(message)
                                  .invalidValue(invalidValue)
                                  .build();
        errorList.add(errorMessage);                                        // 에러 내용 추가

        ErrorResponse errorResponse = ErrorResponse.builder()               // 에러 응답 작성
                                                   .errorList(errorList)
                                                   .requestUrl(httpServletRequest.getRequestURI())
                                                   .statusCode(HttpStatus.BAD_REQUEST.toString())
                                                   .resultCode("FAIL")
                                                   .build();

        log.error("Oops so sick, harmony: {}", message , ex);               // 해당 에러에 대한 메시지와 stacktrace 로깅
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    /**
     * 
     * @param ex
     * @param httpServletRequest
     * @return
     */
    @ExceptionHandler(value = { AuthException.class })
    public ResponseEntity<Object> handleApiRequestException(AuthException ex, HttpServletRequest httpServletRequest) {
        List<Error> errorList = new ArrayList<>();

        String fieldName = "Token Refresh Error";
        String message = ex.getMessage();
        String invalidValue = ex.getInvalidValue();
        String code = ex.getCode();

        Error errorMessage = Error.builder()
                                  .field(fieldName)
                                  .code(code)
                                  .message(message)
                                  .invalidValue(invalidValue)
                                  .build();
        errorList.add(errorMessage);

        ErrorResponse errorResponse = ErrorResponse.builder()
                                                   .errorList(errorList)
                                                   .requestUrl(httpServletRequest.getRequestURI())
                                                   .statusCode(HttpStatus.BAD_REQUEST.toString())
                                                   .resultCode("FAIL")
                                                   .build();

        log.error("Oops so sick, harmony: {}", message , ex);
        return ResponseEntity.badRequest().header(SET_COOKIE, removeRefCookie()).body(errorResponse);
    }

    
    /**
     * 정의되지 않은 모든 에러에 대해 INTERNAL_SERVER_ERROR를 Response
     * @param ex
     * @return 500 INTERNAL_SERVER_ERROR가 담긴, ResponseEntity
     */
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exception(Exception ex){
        log.error("Oops so sick, harmony: {}", ex.getClass().getName() , ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAIL");
    }
}
