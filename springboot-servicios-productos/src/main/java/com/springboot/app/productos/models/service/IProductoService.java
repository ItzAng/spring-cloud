package com.springboot.app.productos.models.service;

import java.util.List;

import com.spring.boot.app.commons.models.entity.Producto;

public interface IProductoService {
	
	public List<Producto> findAll();
	public Producto findById(Long id);
	
	public Producto save(Producto productoNuevo);
	
	public void deleteById(Long id);

}
