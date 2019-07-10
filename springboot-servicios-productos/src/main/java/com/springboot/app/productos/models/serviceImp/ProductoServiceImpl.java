package com.springboot.app.productos.models.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.productos.models.dao.ProdutosDao;
import com.springboot.app.productos.models.entity.Producto;
import com.springboot.app.productos.models.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private ProdutosDao productoDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return productoDAO.findById(id).orElse(null);
	}

}
