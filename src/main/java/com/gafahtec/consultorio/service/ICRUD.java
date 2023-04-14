package com.gafahtec.consultorio.service;

import java.util.List;

import com.gafahtec.consultorio.exception.ResourceNotFoundException;

public interface ICRUD<T, ID> {

	T registrar(T t) throws ResourceNotFoundException;

	T modificar(T t) throws ResourceNotFoundException;

	List<T> listar() throws ResourceNotFoundException;

	T listarPorId(ID id) throws ResourceNotFoundException;

	void eliminar(ID id) throws ResourceNotFoundException;
	
	
}
