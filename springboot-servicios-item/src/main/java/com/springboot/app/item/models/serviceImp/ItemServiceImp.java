package com.springboot.app.item.models.serviceImp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.Producto;
import com.springboot.app.item.models.service.ItemService;

@Service("serviceRestTemplate")
public class ItemServiceImp implements ItemService {

	@Autowired
	private RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {
		//
		List<Producto> productos = List.of(clienteRest.getForObject("http://servicio-productos/listar", Producto[].class));
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

}
