package com.gafahtec.repository;

import com.gafahtec.model.auth.Empresa;
import com.gafahtec.model.auth.Rol;

public interface IEmpresaRepository extends IGenericRepository<Empresa,Integer>{
	
	Rol findByNombre(String nombre);

}
