package com.sea.gestao_usuarios.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sea.gestao_usuarios.dto.EmailDto;
import com.sea.gestao_usuarios.dto.PhoneDto;
import com.sea.gestao_usuarios.dto.UserDto;
import com.sea.gestao_usuarios.modules.usuarios.User;
import com.sea.gestao_usuarios.modules.usuarios.UserEmails;
import com.sea.gestao_usuarios.modules.usuarios.UserTelefone;
import com.sea.gestao_usuarios.modules.usuarios.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }
    
    public UserDto updateUser(UserDto userDto, long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não pode ser encontrado!"));

        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setCpf(userDto.getCpf());

        user.getEndereco().setCep(userDto.getCep());
        user.getEndereco().setLogradouro(userDto.getLogradouro());
        user.getEndereco().setBairro(userDto.getBairro());
        user.getEndereco().setCidade(userDto.getCidade());
        user.getEndereco().setUf(userDto.getUf());
        user.getEndereco().setComplemento(userDto.getComplemento());

        user.getPhoneNumbers().clear();

        List<UserTelefone> updatedPhones = userDto.getPhoneNumbers().stream().map(phoneDto -> {
            if (phoneDto.getTelefone() == null || phoneDto.getTelefone().isEmpty()) {
                throw new RuntimeException("O número de telefone não pode ser nulo ou vazio.");
            }
            if (phoneDto.getTipo() == null || phoneDto.getTipo().isEmpty()) {
                throw new RuntimeException("O tipo de telefone não pode ser nulo ou vazio.");
            }

            UserTelefone phone = new UserTelefone();
            phone.setTelefone(phoneDto.getTelefone());
            phone.setTipo(phoneDto.getTipo());
            phone.setUser(user);
            return phone;
        }).collect(Collectors.toList());

        user.getPhoneNumbers().clear();
        user.getPhoneNumbers().addAll(updatedPhones);
        
        user.getEmails().clear();
        List<UserEmails> updatedEmails = userDto.getEmails().stream().map(emailDto -> {
            if (emailDto.getEmail() == null || emailDto.getEmail().isEmpty()) {
                throw new RuntimeException("O email não pode ser nulo ou vazio.");
            }
            UserEmails email = new UserEmails();
            email.setEmail(emailDto.getEmail());
            email.setUser(user);
            return email;
        }).collect(Collectors.toList());
        
        user.getEmails().clear();
        user.getEmails().addAll(updatedEmails);

        User updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }
    
    public void deleteUserId(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não pode ser deletado!"));
        userRepository.delete(user);
    }

    public UserDto getUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não pode ser encontrado!"));
        return mapToDto(user);
    }
    
    private UserDto mapToDto(User user) {
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
        userDto.setLogin(user.getUsername());
        ArrayList<PhoneDto> phoneDtos = user.getPhoneNumbers().stream()
            .map(phone -> new PhoneDto(phone.getTelefone(), phone.getTipo()))
            .collect(Collectors.toCollection(ArrayList::new));
        userDto.setPhoneNumbers(phoneDtos);

        ArrayList<EmailDto> emailDtos = user.getEmails().stream()
            .map(email -> new EmailDto(email.getEmail()))
            .collect(Collectors.toCollection(ArrayList::new));
        userDto.setEmails(emailDtos);
        
        return userDto;
    }
}

