package com.bravo.meli.adn.negocio.dto;

public class UserDto {
	private String userName;
	private String token;

	public UserDto() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
