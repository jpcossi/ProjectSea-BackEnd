package com.sea.gestao_usuarios.modules.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sea.gestao_usuarios.modules.usuarios.UserTelefone;


public interface TelefoneRepository extends JpaRepository<UserTelefone, Long> {
}