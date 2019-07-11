package com.spring.boot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.spring.boot.app.usuarios.commons.models.entity.Usuario;

@RepositoryRestResource(path = "usuarios")
public interface UsuarioDAO extends CrudRepository<Usuario, Long> {

	@RestResource(path = "findUserName")
	public Usuario findByUsername(String username);
	
	@Query("select u from Usuario u where u.username = ?1 ")
	public Usuario ObtenerPorUserName(String username);
	
//	@Query(nativeQuery = true , value = "select * from usuario where username = ?")
//	public Usuario ObtenerPorUserNameNativo(String username);
	
	
}
