
package com.sea.gestao_usuarios.modules.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sea.gestao_usuarios.modules.usuarios.UserEndereco;


public interface EmailRepository extends JpaRepository<UserEndereco, Long> {
}

