package com.company.books.backend.services;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDAO;
import com.company.books.backend.response.CategoriaResponseRest;

@Service
public class CategoriaServicelmpl implements ICategoriaService{
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaServicelmpl.class);
	@Autowired
	private ICategoriaDAO categoriaDao;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarCategorias() {
		// TODO Auto-generated method stub
		log.info("inicio metodo buscarCartegorias()");
		CategoriaResponseRest response = new CategoriaResponseRest();
		try {
			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			response.getCategoriaResponse().setCategoria(categoria);
			response.setMetada("Respuesta ok", "00", "Respuesta Existosa");
		} catch (Exception e) {
			// TODO: handle exception
			response.setMetada("No respuesta", "-1", "Error al consultar categorias");
			log.error("error al consultar categorias: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);  //status: 500
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);  //status: 200
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
		log.info("Inicio metodo buscarPorId");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		try {
			Optional<Categoria> categoria = categoriaDao.findById(id);
			if(categoria.isPresent()) {
				list.add(categoria.get());
				response.getCategoriaResponse().setCategoria(list);
			}else {
				log.error("Error en consultar categoria");
				response.setMetada("Respuesta no ok", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);  // status:404
			}
		} catch (Exception e) {
			log.error("Error en consultar categoria");
			response.setMetada("Respuesta no ok", "-1", "Categoria no encontrada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);  // status:500
		}
		response.setMetada("Respuesta Ok", "00", "Respuesta existosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);  // status:200
	}
	
}
