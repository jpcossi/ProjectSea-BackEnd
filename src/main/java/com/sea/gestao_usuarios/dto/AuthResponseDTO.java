package com.sea.gestao_usuarios.dto;

public record AuthResponseDTO(String token, UserAuthDto user) {
}