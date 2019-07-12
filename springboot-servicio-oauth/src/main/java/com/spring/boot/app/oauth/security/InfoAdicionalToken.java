package com.spring.boot.app.oauth.security;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import com.spring.boot.app.oauth.services.IUsuarioService;
import com.spring.boot.app.usuarios.commons.models.entity.Usuario;

@Service
public class InfoAdicionalToken implements TokenEnhancer {

	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		var<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("usuario", usuario);
		mapa.put("nombre", usuario.getNombre());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(mapa);

		return accessToken;
	}

}
