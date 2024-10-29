package com.sea.gestao_usuarios.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailDto {
  private String email;

  @JsonCreator
  public EmailDto(@JsonProperty("email") String email) {
    this.email = email;
  }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
