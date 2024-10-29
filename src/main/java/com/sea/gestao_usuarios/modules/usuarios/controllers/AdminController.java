package com.sea.gestao_usuarios.modules.usuarios.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sea.gestao_usuarios.dto.EmailDto;
import com.sea.gestao_usuarios.dto.PhoneDto;
import com.sea.gestao_usuarios.dto.UserDto;
import com.sea.gestao_usuarios.modules.usuarios.User;
import com.sea.gestao_usuarios.modules.usuarios.repository.UserRepository;
import com.sea.gestao_usuarios.services.MyUserDetailsService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("rawtypes")
    @GetMapping("/users")
    public ResponseEntity getAllUsers(){
        List<User> users = userRepository.findAll();
        ArrayList<UserDto> userDtosList = new ArrayList<>();
        users.forEach(user -> {
          UserDto userDto = new UserDto(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getCpf(),
            user.getEndereco().getCep(),
            user.getEndereco().getLogradouro(),
            user.getEndereco().getBairro(),
            user.getEndereco().getCidade(),
            user.getEndereco().getUf());
            
            ArrayList<PhoneDto> phoneDtos = user.getPhoneNumbers().stream()
              .map(phone -> new PhoneDto(phone.getTelefone(), phone.getTipo()))
              .collect(Collectors.toCollection(ArrayList::new));
            userDto.setPhoneNumbers(phoneDtos);

            ArrayList<EmailDto> emailDtos = user.getEmails().stream()
              .map(email -> new EmailDto(email.getEmail()))
              .collect(Collectors.toCollection(ArrayList::new));
            userDto.setEmails(emailDtos);

            userDtosList.add(userDto);
        });
        
        return ResponseEntity.ok(userDtosList);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        return ResponseEntity.ok(myUserDetailsService.getUser(id));
    }

    @PutMapping("/user/{id}/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Long userId) {
        UserDto response = myUserDetailsService.updateUser(userDto, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long userId) {
        myUserDetailsService.deleteUserId(userId);
        return new ResponseEntity<>("Usuário deletado com sucesso!", HttpStatus.OK);
    }
}

