package com.sea.gestao_usuarios.modules.usuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sea.gestao_usuarios.dto.UserDto;
import com.sea.gestao_usuarios.services.MyUserDetailsService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        return ResponseEntity.ok(myUserDetailsService.getUser(id));
    }
    
}

