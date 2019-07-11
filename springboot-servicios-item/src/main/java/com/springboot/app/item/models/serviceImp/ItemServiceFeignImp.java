package com.springboot.app.item.models.serviceImp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.app.commons.models.entity.Producto;
import com.springboot.app.item.clientes.ProductoClienteRestFeign;
import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.service.ItemService;

@Service("feign")
public class ItemServiceFeignImp implements ItemService {

	@Autowired
	private ProductoClienteRestFeign clienteFeign;
	@Override
	public List<Item> findAll() {
		// TODO Auto-generated method stub
		return clienteFeign.listar().stream().map(a-> new Item(a,1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		// TODO Auto-generated method stub
		System.out.println("Esto es por feign");
		return new Item(clienteFeign.detalle(id),cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		// TODO Auto-generated method stub
		return clienteFeign.crear(producto);
	}

	@Override
	public Producto update(Producto producto, Long id) {
		// TODO Auto-generated method stub
		return clienteFeign.editar(producto, id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clienteFeign.delete(id);
	}

}
