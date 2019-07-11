package com.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.productos.models.entity.Producto;
import com.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {

	@Autowired
	private IProductoService productoService;

	@Autowired
	private Environment env;

	@Value("${server.port}")
	private Integer port;

	@GetMapping("/listar")
	public List<Producto> listar() {
		return productoService.findAll().stream().map(a -> {
			a.setPort(Integer.valueOf(env.getProperty("local.server.port")));
			return a;
		}).collect(Collectors.toList());
	}

	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) {

		Producto producto = productoService.findById(id);
		producto.setPort(Integer.valueOf(env.getProperty("local.server.port")));
//		boolean ok = true;
//		if(ok == false) {
//			throw new Exception("No se pudo cargar el producto");
//		}
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return producto;
	}

	@PostMapping("/crear")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Producto save(@RequestBody Producto producto) {
		return productoService.save(producto);
	}
	
	@DeleteMapping("/producto/{id}")
	public void d() {
		
	}
}
