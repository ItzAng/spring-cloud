package com.spring.boot.app.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SpringbootServicioOauthApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioOauthApplication.class, args);
	}

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {
//		String contraseña ="12345";
//		for(int i = 0; i <4 ; i++) {
//			String passwordBcryp = passwordEncoder.encode(contraseña);
//			System.out.println(passwordBcryp);
//		}
		
	}

}
