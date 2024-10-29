package com.sea.gestao_usuarios.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sea.gestao_usuarios.modules.usuarios.User;
import com.sea.gestao_usuarios.modules.usuarios.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JWTGenerator tokenGenerator;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
        String token = getJWTFromRequest(request);
        System.out.println("TOKEn no filter" + token);
        if(token != null){
          var login = tokenGenerator.validateToken(token);
          long id = Long.parseLong(login);
          User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não pode ser encontrado!"));
          System.out.println("Login no filter:    " + login);
          System.out.println("USER no filter:   " + user);
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
    
    private String getJWTFromRequest(HttpServletRequest request){
      String bearerToken = request.getHeader("Authorization");
      if( bearerToken != null && bearerToken.startsWith("Bearer ")){
        return bearerToken.substring(7, bearerToken.length());
      }

      return null;
    }
  
}
