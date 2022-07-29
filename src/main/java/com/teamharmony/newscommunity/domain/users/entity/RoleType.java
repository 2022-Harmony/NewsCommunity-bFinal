package com.teamharmony.newscommunity.domain.users.entity;

public enum RoleType {
	USER("USER") {
		public String toString() {
			return "ROLE_USER";
		}},  // 기본 사용자 권한
	ADMIN("ADMIN") {
		public String toString() {
			return "ROLE_ADMIN";
		}};  // 관리자 권한
	
	public final String roleName;
	
	RoleType(String roleName) {
		this.roleName = roleName;
	}
}