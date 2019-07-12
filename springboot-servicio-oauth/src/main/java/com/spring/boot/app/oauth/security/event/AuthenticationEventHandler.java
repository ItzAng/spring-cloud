package com.spring.boot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.spring.boot.app.oauth.services.IUsuarioService;

import feign.FeignException;

@Component
public class AuthenticationEventHandler implements AuthenticationEventPublisher {

	private static Logger log = LoggerFactory.getLogger(AuthenticationEventHandler.class);

	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		var user = ((UserDetails) authentication.getPrincipal());
		var userDB = usuarioService.findByUsername(authentication.getName());
		if (userDB.getIntentos() == null) {
			userDB.setIntentos(0);
		} else {
			userDB.setIntentos(0);
			usuarioService.update(userDB, userDB.getId());
		}

		log.info("Usuario conectado : " + " " + user.getUsername());
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		try {
			var user = usuarioService.findByUsername(authentication.getName());

			if (user.getIntentos() == null) {
				user.setIntentos(0);
			}
			user.setIntentos(user.getIntentos() + 1);

			if (user.getIntentos() == 3) {
				user.setEnabled(false);
			}
			usuarioService.update(user, user.getId());
			log.error("Error en el Login " + exception.getMessage());
		} catch (FeignException e) {
			log.error(String.format("El usuario no existe en el sistema", authentication.getName()));
		}
	}

}
