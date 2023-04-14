package com.gafahtec.consultorio.service.impl;
import java.util.List;

import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.service.ICRUD;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

	protected abstract IGenericRepository<T, ID> getRepo();
	
	@Override
	public T registrar(T t) throws ResourceNotFoundException {
		return getRepo().save(t);
	}

	@Override
	public T modificar(T t) throws ResourceNotFoundException {
		return getRepo().save(t);
	}

	@Override
	public List<T> listar() throws ResourceNotFoundException {		
		return getRepo().findAll();
	}

	@Override
	public T listarPorId(ID id) throws ResourceNotFoundException {
		return getRepo().findById(id).orElse(null);
	}

	@Override
	public void eliminar(ID id) throws ResourceNotFoundException {
		getRepo().deleteById(id);
	}

}
