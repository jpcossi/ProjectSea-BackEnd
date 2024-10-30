package com.sea.gestao_usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class GestaoUsuariosApplication {


	
	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
        
		// Configurando variáveis de ambiente
		System.setProperty("MYSQL_USER", dotenv.get("MYSQL_USER"));
		System.setProperty("MYSQL_PASSWORD", dotenv.get("MYSQL_PASSWORD"));
		System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
		
		SpringApplication.run(GestaoUsuariosApplication.class, args);
	}

}
