package com.spring.boot.app.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.spring.boot.app.usuarios.commons.models.entity.Role;
import com.spring.boot.app.usuarios.commons.models.entity.Usuario;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer{

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		// TODO Auto-generated method stub
//		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config);
		config.exposeIdsFor(Usuario.class,Role.class);
	}

	
}
