package com.teamharmony.newscommunity.domain.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamharmony.newscommunity.exception.AuthException;
import com.teamharmony.newscommunity.exception.Error;
import com.teamharmony.newscommunity.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (Exception ex) {
			setErrorResponse(request, response, ex);
		}
	}

	public void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
		log.error("Oops so sick, harmony: {}", ex.getClass().getName() + "\t" + ex.getMessage(), ex);

		List<Error> errorList = new ArrayList<>();
		ErrorResponse errorResponse = null;

		String fieldName = "Auth Filter Error";
		String message = ex.getMessage();

		if (ex instanceof AuthException) {
			String invalidValue = ((AuthException) ex).getInvalidValue();
			String code = ((AuthException) ex).getCode();
			Error errorMessage = Error.builder()
			                          .field(fieldName)
			                          .code(code)
			                          .message(message)
			                          .invalidValue(invalidValue)
			                          .build();
			errorList.add(errorMessage);
			errorResponse = ErrorResponse.builder()
			                             .errorList(errorList)
			                             .requestUrl(request.getRequestURI())
			                             .statusCode(FORBIDDEN.toString())
			                             .resultCode("FAIL")
			                             .build();
			response.setStatus(FORBIDDEN.value());
		} else {
			Error errorMessage = Error.builder()
			                          .field(fieldName)
			                          .build();
			errorList.add(errorMessage);
			errorResponse = ErrorResponse.builder()
			                             .errorList(errorList)
			                             .requestUrl(request.getRequestURI())
			                             .statusCode(INTERNAL_SERVER_ERROR.toString())
			                             .resultCode("FAIL")
			                             .build();
			response.setStatus(INTERNAL_SERVER_ERROR.value());
		}
		response.setContentType(APPLICATION_JSON_VALUE);
		response.setHeader("error", message);
		try {
			new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}