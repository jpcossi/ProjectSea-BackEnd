package com.sea.gestao_usuarios.dto;

public class PhoneDto {
  private String telefone;
  private String tipo;

  public PhoneDto(String telefone, String tipo) {
    this.telefone = telefone;
    this.tipo = tipo;
  }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
