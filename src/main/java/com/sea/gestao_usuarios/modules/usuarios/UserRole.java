package com.sea.gestao_usuarios.modules.usuarios;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}