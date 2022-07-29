package com.teamharmony.newscommunity.domain.auth.util;

import com.teamharmony.newscommunity.exception.AuthException;
import org.springframework.http.ResponseCookie;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
	// 갱신 토큰이 저장된 쿠키 값 가져오기
	public static String getRefCookie(HttpServletRequest request) throws AuthException {
		Cookie[] cookies = request.getCookies();
		Cookie refCookie = null;
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName()
				         .equals("ref_uid")) {
					refCookie = cookie;
				}
			}
		}
		if(refCookie == null)	throw AuthException.builder().message("갱신 토큰을 찾을 수 없습니다.").code("A408").build();
		return refCookie.getValue();
	}
	
	// 리프레쉬 쿠키 삭제(만료시간 0으로 설정)
	public static String removeRefCookie() {
		ResponseCookie refresh = ResponseCookie.from("ref_uid", "")
		                                       .maxAge(0)
		                                       .path("/")
		                                       .build();
		return refresh.toString();
	}
}
