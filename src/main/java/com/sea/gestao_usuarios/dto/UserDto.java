package com.sea.gestao_usuarios.dto;

import java.util.List;

public class UserDto {
  
  private long id;
  private String login;
  private String name;
  private String password;
  private String cpf;
  private String cep;
  private String logradouro;
  private String bairro;
  private String cidade;
  private String uf;
  private String complemento;
  private List<PhoneDto> phoneNumbers;
  private List<EmailDto> emails;

  public UserDto(long id, String login, String name, String cpf, String cep, String logradouro, String bairro, String cidade, String uf ){
      this.id = id;
      this.login = login;
      this.name = name;
      this.cpf = cpf;
      this.cep = cep;
      this.logradouro = logradouro;
      this.bairro = bairro;
      this.cidade = cidade;
      this.uf = uf;
  }
  
  @Override
  public String toString() {
    return "UserDto{" +
    "Username='" + login + '\'' +
    ", Nome='" + name + '\'' +
    ", CPF='" + cpf + '\'' +
    '}';
  }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public List<PhoneDto> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneDto> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<EmailDto> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailDto> emails) {
        this.emails = emails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

    