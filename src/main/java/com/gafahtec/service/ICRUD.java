package com.gafahtec.service;

import java.util.Set;

public interface ICRUD<RQ, RS, ID> {

	RS registrar(RQ request);

	RS modificar(RQ request);

	Set<RS> listar();

	RS listarPorId(ID id);

	void eliminar(ID id);
	
	
}
