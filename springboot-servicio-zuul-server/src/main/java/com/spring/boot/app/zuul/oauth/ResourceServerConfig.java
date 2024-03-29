package com.spring.boot.app.zuul.oauth;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${config.security.oauth.jwt.key}")
	private String jwtKey;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/api/security/oauth/token").permitAll()
				.antMatchers(HttpMethod.GET, "/api/productos/listar", "/api/items/listar", "/api/usuarios/usuarios")
				.permitAll()
				.antMatchers(HttpMethod.GET, "/api/productos/ver/{id}", "/api/items/ver/{id}/{cantidad}",
						"/api/usuarios/usuarios/{id}")
				.hasAnyRole("ADMIN", "USER").antMatchers(HttpMethod.POST, "/api/productos/crear", "/api/items/crear")
				.hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/productos/editar/{id}", "/api/items/editar/{id}",
						"/api/usuarios/usuarios/{id}")
				.hasRole("ADMIN").antMatchers(HttpMethod.DELETE, "/api/producto/eliminar/{id}",
						"/api/items/eliminar/{id}", "/api/usuarios/usuarios/{id}")
				.hasRole("ADMIN");
		http.cors().configurationSource(CorsConfigurationSource());
	}

	@Bean
	public CorsConfigurationSource CorsConfigurationSource() {
		CorsConfiguration corsConfiguracion = new CorsConfiguration();
		corsConfiguracion.addAllowedOrigin("*");
		corsConfiguracion.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "OPTIONS"));
		corsConfiguracion.setAllowCredentials(true);
		corsConfiguracion.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		UrlBasedCorsConfigurationSource urlSource = new UrlBasedCorsConfigurationSource();
		urlSource.registerCorsConfiguration("/**", corsConfiguracion);

		return urlSource;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		var filterCors = new FilterRegistrationBean<CorsFilter>(new CorsFilter(CorsConfigurationSource()));
		filterCors.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filterCors;
	}

	@Bean
	public JwtTokenStore tokenStore() {

		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		var tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtKey);
		return tokenConverter;
	}
}
