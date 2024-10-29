package com.sea.gestao_usuarios.modules.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sea.gestao_usuarios.modules.usuarios.UserEndereco;


public interface EnderecoRepository extends JpaRepository<UserEndereco, Long> {
    @SuppressWarnings("null")
    @Override
    Optional<UserEndereco> findById(Long id);
}

