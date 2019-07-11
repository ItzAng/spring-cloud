package com.springboot.app.item.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.Producto;
import com.springboot.app.item.models.service.ItemService;

@RefreshScope
@RestController
public class ItemController {

	@Autowired
	@Qualifier("feign")
	private ItemService itemService;

	@Value("${configuracion.texto}")
	private String texto;

	@Autowired
	private Environment env;

//	@Autowired
//	private org.springframework.cloud.config.environment.Environment env2;

	@GetMapping("/listar")
	public List<Item> listar() {
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver/{id}/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {

		return itemService.findById(id, cantidad);
	}

	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtener_config(@Value("${server.port}") String puerto) {

		System.out.println(texto);
		System.out.println(env.getProperty("configuracion.autor"));
		
		if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			return ResponseEntity.ok(
					Map.of("texto", texto, 
							"puerto", puerto, 
							"autor", env.getProperty("configuracion.autor"),
							"email", env.getProperty("configuracion.email")
									));
		}
		return ResponseEntity.ok(Map.of("texto", texto, "puerto", puerto));
	}

	public Item metodoAlternativo(Long id, Integer cantidad) {
		Item item = new Item();
		Producto producto = new Producto();

		producto.setId(id);
		producto.setNombre("Camara sony");
		producto.setPrecio(40.00);
		item.setProducto(producto);
		item.setCantidad(cantidad);

		return item;
	}
}
