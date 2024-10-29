package com.sea.gestao_usuarios.modules.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.sea.gestao_usuarios.modules.usuarios.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public UserDetails findByLogin(String login);
    Optional<User> findUserByLogin(String login);
}

