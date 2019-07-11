package com.springboot.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.spring.boot.app.commons.models.entity.Producto;

public interface ProdutosDao extends CrudRepository<Producto	, Long> {

}
