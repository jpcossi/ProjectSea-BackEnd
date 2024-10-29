package com.sea.gestao_usuarios.modules.usuarios.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sea.gestao_usuarios.config.JWTGenerator;
import com.sea.gestao_usuarios.dto.AuthResponseDTO;
import com.sea.gestao_usuarios.dto.LoginDto;
import com.sea.gestao_usuarios.dto.RegisterDto;
import com.sea.gestao_usuarios.dto.UserAuthDto;
import com.sea.gestao_usuarios.modules.usuarios.User;
import com.sea.gestao_usuarios.modules.usuarios.UserEmails;
import com.sea.gestao_usuarios.modules.usuarios.UserEndereco;
import com.sea.gestao_usuarios.modules.usuarios.UserRole;
import com.sea.gestao_usuarios.modules.usuarios.UserTelefone;
import com.sea.gestao_usuarios.modules.usuarios.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTGenerator jwtGenerator;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(userRepository.findByLogin(registerDto.getLogin()) != null){
            return new ResponseEntity<>("Usuário já existe, tente novamente com outro nome!", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        UserEndereco endereco = new UserEndereco();
        
        user.setLogin(registerDto.getLogin());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        
        user.setRole(UserRole.USER);
        user.setName(registerDto.getName());
        user.setCpf(registerDto.getCpf());

        String name = user.getName();
        if(!name.matches("[a-zA-Z0-9 ]+")){
            return new ResponseEntity<>("O nome pode incluir apenas letras, números e espaços.", HttpStatus.BAD_REQUEST);
        }

        String login = user.getName();
        if(login.length() < 3 ){
            return new ResponseEntity<>("O login deve ter no minimo 3 caracteres.", HttpStatus.BAD_REQUEST);
        }

        endereco.setCep(registerDto.getCep());
        endereco.setLogradouro(registerDto.getLogradouro());
        endereco.setBairro(registerDto.getBairro());
        endereco.setCidade(registerDto.getCidade());
        endereco.setUf(registerDto.getUf());
        if(!"".equals(registerDto.getComplemento())){
            endereco.setComplemento(registerDto.getComplemento());
        }{
            endereco.setComplemento("");
        }

        List<UserTelefone> phones;
        try{
            phones = registerDto.getPhoneNumbers().stream()
            .map(phoneDto -> {
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

        }catch(Exception ex){
            return new ResponseEntity<>("Pelo menos um número de telefone deve ser fornecido.", HttpStatus.BAD_REQUEST);
        }

        List<UserEmails> emails;
        try{
            emails = registerDto.getEmails().stream()
            .map(emailDto -> {
                if (emailDto.getEmail() == null || emailDto.getEmail().isEmpty()) {
                    throw new RuntimeException("O email não pode ser nulo ou vazio.");
                }
                UserEmails email = new UserEmails();
                email.setEmail(emailDto.getEmail());
                email.setUser(user);
                return email;
            }).collect(Collectors.toList());

            if(emails.isEmpty()){
                return new ResponseEntity<>("O campo do email está vazio.", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception ex){
            return new ResponseEntity<>("Pelo menos um e-mail deve ser fornecido.", HttpStatus.BAD_REQUEST);
        }
        System.out.println("USER DO REGISTER             " + user.toString());

        if (user.isAnyFieldNull()) {
            return new ResponseEntity<>("Um ou mais campos do usuário estão vazios.", HttpStatus.BAD_REQUEST);
        }

        if (endereco.isEnderecoInvalid()) {
            return new ResponseEntity<>("Um ou mais campos do endereco estão vazios.", HttpStatus.BAD_REQUEST);
        }
        
        user.setPhoneNumbers(phones);
        user.setEmails(emails);
        user.setEndereco(endereco);
        endereco.setUser(user);
        
        userRepository.save(user);
        return new ResponseEntity<>(userRepository.save(user) != null ? "Usuário registrado com sucesso!" : "Erro ao registrar usuário!", HttpStatus.OK);
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        
        System.out.println("LoginDto" + loginDto);
        if (loginDto.getLogin() == null) {
            System.out.println("Login no LoginDto" + loginDto.getLogin());
            throw new  RuntimeException("Login Nulo");
        }

        Optional<User> userOptional = userRepository.findUserByLogin(loginDto.getLogin());
        if(!userOptional.isPresent()){
            return new ResponseEntity<>("Usuário não encontrado ou dados estão incorretos, tente novamente!", HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();

        boolean passwordMatches = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
        if (!passwordMatches) {
            return new ResponseEntity<>("Senha incorreta", HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        System.out.println("AUTHHENTICARTION:   " + authentication);
        String token = jwtGenerator.generateToken((User) authentication.getPrincipal());
        UserAuthDto userAuthDto = new UserAuthDto(user.getId(), user.getName(), user.getRole().getRole());
        System.out.println("TOKEN:   " + token);
        System.out.println("USEAUTH:   " + userAuthDto);

        return new ResponseEntity<>(new AuthResponseDTO(token, userAuthDto), HttpStatus.OK);
    }
}

