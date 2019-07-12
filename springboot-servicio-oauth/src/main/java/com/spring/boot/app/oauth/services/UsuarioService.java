package com.spring.boot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.boot.app.oauth.clients.UsuarioFeignClient;
import com.spring.boot.app.usuarios.commons.models.entity.Usuario;

@Service
public class UsuarioService implements UserDetailsService  , IUsuarioService{

	@Autowired
	private UsuarioFeignClient usuarioClientFeign;

	private static Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = findByUsername(username);

		if (user == null) {
			log.info("No existe este usuario");
			throw new UsernameNotFoundException("No existe este usuario");
		}

		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(a -> new SimpleGrantedAuthority(a.getNombre())).peek(a -> log.info("Role"))
				.collect(Collectors.toList());
		log.info("Usuario : " + username);
		return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}

	@Override
	public Usuario findByUsername(String username) {
		// TODO Auto-generated method stub
		return usuarioClientFeign.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		// TODO Auto-generated method stub
		return usuarioClientFeign.update(usuario, id);
	}

}
