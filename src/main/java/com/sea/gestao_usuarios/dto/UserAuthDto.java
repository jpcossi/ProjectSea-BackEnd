package com.sea.gestao_usuarios.dto;

public class UserAuthDto {
    private String name;
    private String role;
    private Long id;

    public UserAuthDto(Long id, String name, String role) {
        this.name = name;
        this.role = role;
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}