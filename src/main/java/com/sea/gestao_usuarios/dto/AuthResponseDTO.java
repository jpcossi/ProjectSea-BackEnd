package com.sea.gestao_usuarios.dto;

public class AuthResponseDTO {
  private String token;
  private UserAuthDto user;

  // Construtor
  public AuthResponseDTO(String token, UserAuthDto user) {
      this.token = token;
      this.user = user;
  }

  // Getters
  public String getToken() {
      return token;
  }

  public UserAuthDto getUser() {
      return user;
  }

    public void setUser(UserAuthDto user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }
}