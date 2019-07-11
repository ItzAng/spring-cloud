package com.springboot.app.item.models.serviceImp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.boot.app.commons.models.entity.Producto;
import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.service.ItemService;

@Service("serviceRestTemplate")
public class ItemServiceImp implements ItemService {

	@Autowired
	private RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {
		//
		List<Producto> productos = List
				.of(clienteRest.getForObject("http://servicio-productos/listar", Producto[].class));
		return productos.stream().map(a -> new Item(a, 1)).collect(Collectors.toList());
//		return null;
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		// TODO Auto-generated method stub
		var producto = clienteRest.getForObject("http://servicio-productos/ver/{id}", Producto.class,
				Map.of("id", id.toString()));
		return new Item(producto, cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		var body = new HttpEntity<Producto>(producto);
		var productoR = clienteRest.exchange("http://servicio-productos/crear", HttpMethod.POST, body, Producto.class);
		return productoR.getBody();
	}

	@Override
	public Producto update(Producto producto, Long id) {

		var body = new HttpEntity<Producto>(producto);

		var productoM = clienteRest.exchange("http://servicio-productos/editar/{id}", HttpMethod.PUT, body,
				Producto.class, Map.of("id",id));

		return productoM.getBody();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
		clienteRest.delete("http://servicio-productos/eliminar/{id}",Map.of("id",id));

	}

}
